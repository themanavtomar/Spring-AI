package com.manav.SpringAI.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.manav.SpringAI.model.User;
import com.manav.SpringAI.repository.UserRepository;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/api/support/**", "/oauth2/**", "/login/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler((request, response, authentication) -> {
                    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                    
                    // GitHub provides "login", Google provides "email" and "name"
                    String email = oAuth2User.getAttribute("email");
                    String name = oAuth2User.getAttribute("name");
                    if (email == null) {
                        email = oAuth2User.getAttribute("login") + "@github.com";
                    }
                    if (name == null) {
                        name = oAuth2User.getAttribute("login");
                    }

                    // Save or update user in MongoDB
                    final String finalEmail = email;
                    final String finalName = (name != null) ? name : "Developer";
                    User user = userRepository.findByEmail(finalEmail).orElseGet(() -> {
                        User newUser = new User();
                        newUser.setEmail(finalEmail);
                        newUser.setName(finalName);
                        newUser.setAuthProvider("OAUTH2");
                        newUser.setProfession("Engineer");
                        newUser.setCompany("Enterprise");
                        newUser.setAge(22);
                        newUser.setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=" + finalName);
                        newUser.setCreatedAt(LocalDateTime.now());
                        return userRepository.save(newUser);
                    });

                    // Redirect back to frontend with user details in query params
                    String redirectUrl = "http://localhost:5173/?email=" + URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8)
                            + "&name=" + URLEncoder.encode(user.getName(), StandardCharsets.UTF_8);
                    response.sendRedirect(redirectUrl);
                })
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
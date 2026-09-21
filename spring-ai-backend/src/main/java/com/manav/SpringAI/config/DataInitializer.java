package com.manav.SpringAI.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.manav.SpringAI.model.User;
import com.manav.SpringAI.repository.UserRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("No users found. Creating initial user to initialize MongoDB database...");

            User initialUser = new User();
            initialUser.setName("Alex");
            initialUser.setEmail("alex@cognizant.com");
            initialUser.setAuthProvider("LOCAL");
            initialUser.setProfession("Java Backend Engineer");
            initialUser.setCompany("Cognizant");
            initialUser.setAge(21);
            initialUser.setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=Alex");
            initialUser.setCreatedAt(LocalDateTime.now());

            userRepository.save(initialUser);
            log.info("Default user saved successfully. MongoDB database and collections are now created!");
        } else {
            log.info("Database already initialized. Found {} users.", userRepository.count());
        }
    }
}
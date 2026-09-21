package com.manav.SpringAI.service;

import com.manav.SpringAI.model.ChatAudit;
import com.manav.SpringAI.repository.ChatAuditRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupportAgentService {

    private final OllamaChatModel localModel;
    private final GoogleGenAiChatModel cloudModel;
    private final ChatAuditRepository auditRepository;

    public String chatWithLocalDeepSeek(
            String prompt,
            String userEmail) {

        log.info(
                "Generating response from local DeepSeek for: {}",
                userEmail
        );

        String response = localModel.call(prompt);

        saveAudit(
                userEmail,
                prompt,
                response,
                "DeepSeek-Local"
        );

        return response;
    }

    public String chatWithCloudGemini(
            String prompt,
            String userEmail) {

        log.info(
                "Generating response from Gemini Cloud for: {}",
                userEmail
        );

        String response = cloudModel.call(prompt);

        saveAudit(
                userEmail,
                prompt,
                response,
                "Gemini-Cloud"
        );

        return response;
    }

    private void saveAudit(
            String userEmail,
            String prompt,
            String response,
            String modelUsed) {

        ChatAudit audit = new ChatAudit();

        audit.setUserEmail(
                userEmail != null && !userEmail.isBlank()
                        ? userEmail
                        : "anonymous"
        );

        audit.setPrompt(prompt);
        audit.setAiResponse(response);
        audit.setModelUsed(modelUsed);
        audit.setTimestamp(LocalDateTime.now());

        auditRepository.save(audit);

        log.info(
                "Chat conversation saved. Model: {}",
                modelUsed
        );
    }
}
package com.manav.SpringAI.controller;

import com.manav.SpringAI.dto.ChatRequest;
import com.manav.SpringAI.service.SupportAgentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class AgentController {

    private final SupportAgentService service;

    @PostMapping("/local")
    public ResponseEntity<String> askLocal(
            @RequestBody ChatRequest request) {

        String response = service.chatWithLocalDeepSeek(
                request.getPrompt(),
                request.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cloud")
    public ResponseEntity<String> askCloud(
            @RequestBody ChatRequest request) {

        String response = service.chatWithCloudGemini(
                request.getPrompt(),
                request.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}
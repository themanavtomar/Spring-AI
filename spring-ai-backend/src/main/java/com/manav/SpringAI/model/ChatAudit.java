package com.manav.SpringAI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat_audits")
public class ChatAudit {
    @Id
    private String id;
    private String userEmail; // Links to the User model
    private String prompt;
    private String aiResponse;
    private String modelUsed; // "DeepSeek-Local" or "Gemini-Cloud"
    private LocalDateTime timestamp;
}
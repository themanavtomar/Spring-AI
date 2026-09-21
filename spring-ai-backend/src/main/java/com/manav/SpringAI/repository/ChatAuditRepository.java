package com.manav.SpringAI.repository;

import com.manav.SpringAI.model.ChatAudit;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatAuditRepository extends MongoRepository<ChatAudit, String> {
    List<ChatAudit> findByUserEmailOrderByTimestampDesc(String email);
}
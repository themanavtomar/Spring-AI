package com.manav.SpringAI.model;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

	@Id
	private String id;

	private String email;

	private String password;

	private String authProvider;

	private String name;

	private String avatar;

	private Integer age;

	private String profession;

	private String company;

	private LocalDateTime createdAt;
}
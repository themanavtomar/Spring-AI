# Spring AI Support Agent

A full-stack AI-powered support agent built using Spring Boot, Spring AI, React, and MongoDB Atlas.

The application supports AI chat using both local and cloud-based AI models, along with user authentication and OAuth login.

## 🚀 Features

- AI-powered support chat
- Google Gemini cloud AI integration
- Ollama + DeepSeek local AI integration
- Google OAuth 2.0 login
- GitHub OAuth 2.0 login
- Local email/password authentication
- BCrypt password encryption
- MongoDB Atlas database
- Chat history/audit storage
- React + Vite frontend
- Spring Boot REST API
- AWS Elastic Beanstalk deployment
- AWS Secrets Manager for sensitive configuration

## 🏗️ Project Structure

```text
Spring-AI/
│
├── spring-ai-frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── vite.config.js
│
└── spring-ai-backend/
    ├── src/
    ├── pom.xml
    └── ...

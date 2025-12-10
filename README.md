# Collaborative Study Platform

A JavaFX + Spring Boot platform for collaborative student learning.  
The application is designed as a hub for study groups: creating groups, managing members, and (in the next iterations) enabling real-time communication and file sharing.

## Features

Current / planned features:

- JavaFX desktop client
- Spring Boot backend
- Persistence with a relational database (`study_platform.db`)
- Study groups and users (under development)
- Planned:
  - Real-time group chat (WebSockets)
  - File sharing inside groups
  - Notifications for shared files and new messages
  - Roles and permissions inside a group

## Tech Stack

**Backend**

- Java
- Spring Boot
- Spring Web (REST)
- Spring WebSocket (for real-time chat)
- Spring Data JPA / Hibernate

**Client**

- JavaFX desktop application

**Database**

- SQLite (`study_platform.db`) by default

## Project Structure (simplified)

```text
Collaborative-Study-Platform/
├── pom.xml
├── study_platform.db
├── src/
│   └── main/
│       ├── java/
│       │   └── ... (backend + JavaFX client packages)
│       └── resources/
│           ├── application.properties
│           └── ...
└── .idea/      # IDE configuration (not used at runtime)

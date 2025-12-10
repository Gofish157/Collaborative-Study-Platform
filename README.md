# Collaborative Study Platform (Server)

Backend service for a collaborative study platform.  
The server exposes REST APIs for authentication, users, groups, tasks and (in the next iterations) real-time communication via WebSockets.

This project is intended as a learning and experimentation base for building a full collaborative study environment (desktop or web client on top of this API).

---

## Features

### Implemented

- User registration and authentication (JWT-based)
- Basic security configuration with Spring Security
- Study groups:
  - Creating groups
  - Managing memberships
- Tasks:
  - Creating and assigning tasks to groups or users
  - Storing task metadata in the database
- User management (viewing, updating user data)
- Central services and repositories for domain logic
- CORS configuration for a separate frontend / desktop client

### In Progress / Planned

- WebSocket-based real-time group chat
- File sharing inside groups (HTTP upload + WebSocket notifications)
- Per-group chat rooms
- Better error handling and API documentation (OpenAPI/Swagger)
- Integration with a JavaFX or web-based client

---

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Web:** Spring Web (REST), Spring Security, CORS configuration
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** SQLite (`study_platform.db`)
- **Security:** JWT (JSON Web Token) authentication
- **Real-time:** Spring WebSocket (endpoint wired at `/ws/chat`)

---

## Project Structure

```text
src/main/java/com/studyplatform/server
├── ServerApplication.java        # Spring Boot entry point
│
├── config                        # Configuration classes
│   ├── CorsConfig.java           # CORS configuration
│   ├── SecurityConfig.java       # Spring Security + JWT
│   └── WebSocketConfig.java      # WebSocket endpoint configuration
│
├── controllers                   # REST controllers
│   ├── AuthController.java       # /auth: login, register
│   ├── DashboardController.java  # dashboard-related endpoints
│   ├── GroupController.java      # groups and memberships
│   ├── TaskController.java       # tasks
│   └── UserController.java       # user management
│
├── dto                           # Data Transfer Objects
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── GroupDTO.java
│   ├── GroupRequestDTO.java
│   ├── RegisterUserDTO.java
│   ├── TaskDTO.java
│   ├── UpdateUserDTO.java
│   └── UserDTO.java
│
├── models                        # JPA entities
│   ├── Group.java
│   ├── Membership.java
│   ├── Resource.java
│   ├── Task.java
│   ├── User.java
│   └── LocalDateTimeConverter.java
│
├── repositories                  # Spring Data repositories
│   ├── GroupRepository.java
│   ├── MembershipRepository.java
│   ├── ResourceRepository.java
│   ├── TaskRepository.java
│   └── UserRepository.java
│
├── security                      # JWT integration with Spring Security
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   ├── UserDetailsImpl.java
│   └── UserDetailsServiceImpl.java
│
├── services                      # Business logic
│   ├── AuthService.java
│   ├── GroupService.java
│   ├── MembershipService.java
│   ├── ResourceService.java
│   ├── TaskService.java
│   └── UserService.java
│
└── websocket (planned/added)     # WebSocket handlers (see below)
    └── ChatWebSocketHandler.java # basic broadcast chat handler


---
##Authentication & Security

The server uses JWT-based authentication integrated with Spring Security.

Typical flow:

Client sends credentials to the authentication endpoint (e.g. /auth/login) with an AuthRequest payload.

On success, the server returns an AuthResponse containing a JWT token.

For all subsequent requests, the client must send the token in the Authorization header:

Authorization: Bearer <token>


User details are loaded via UserDetailsServiceImpl, and the token is validated in JwtFilter.

See AuthController, AuthService, JwtUtil, and SecurityConfig for the exact behavior and configuration.

---

File Sharing (Planned)

Planned architecture for file sharing inside study groups:

Upload:
Files are uploaded via standard HTTP POST requests (e.g. multipart/form-data) to a dedicated upload endpoint.
The backend stores files on disk or in object storage and records metadata in the database (e.g. via the Resource entity).

Notification:
After a file is uploaded successfully, the backend sends a WebSocket notification to the relevant group’s chat channel, so all members immediately see that a new file is available (with its name and a download URL).

This design keeps file transfer efficient and uses WebSockets only for lightweight real-time updates.

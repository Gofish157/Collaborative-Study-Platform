# Collaborative Study Platform

Collaborative Study Platform is a **web-based application** designed to help students organize learning materials, manage study content, and work with educational data in a structured way.

The project follows a classic **web-application architecture** with a Java backend and a separate frontend layer.

---

## Tech Stack

### Backend
- **Java**
- **Maven**
- **Spring ecosystem** (Spring Boot / MVC)
- Embedded database (for development)

### Frontend
- **Web application** (HTML / CSS / JavaScript)
- Separate frontend module
- Built as a client-side interface for the backend API

---

## Features

- Web-based user interface
- Backend API built with Java and Maven
- Persistent storage via embedded database
- Modular separation between backend and frontend
- Designed for further extension (authentication, roles, collaboration logic)

---

## Getting Started

### Prerequisites
- **Java 17+**
- **Maven**

---

### Backend Setup

bash:
mvn clean install
mvn spring-boot:run

The backend application will start on the default Spring Boot port.

### Frontend Setup
cd frontend
npm install
npm run dev

---

### Development Notes

The project is intended as a web application, not a desktop application.

No WebSockets are currently used.

The architecture allows easy future expansion (REST API extensions, authentication, real-time features if needed later).

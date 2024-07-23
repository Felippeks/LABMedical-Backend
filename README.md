# Labmedical Project

## Overview
Labmedical is a backend REST API developed in Java, utilizing the Spring Boot framework. This project aims to provide a comprehensive system for managing patient records, consultations, and medical examinations.

## Application Requirements

- **Language and Framework**: The application is developed in Java, using Spring Boot.
- **Architecture**: Follows the MVC pattern with Controller, Service, and Repository layers.
- **Security**: Implements Spring Security for access control.
- **Data Transfer**: Utilizes DTOs for request and response handling.
- **Database**: Uses PostgreSQL for data storage, which can be set up via Docker or direct connection.
- **Version Control**: Code is managed on GitHub, adhering to the GitFlow pattern with main, features, and optional release branches. Commits are expected to be short and concise.
- **Task Management**: Tasks are organized using Trello, with a public board following the Kanban structure (backlog, todo, doing, blocked, review, done).

## Application Roadmap

### System Format

The application will include the following endpoints, secured with JWT tokens via Spring Security, except for the login endpoint which is unrestricted.

#### Endpoints

- **Login Endpoint**
- **Registration Endpoint**
- **Patient Entity Endpoints**
    - List all patients
- **Consultation Entity Endpoints**
- **Examination Entity Endpoints**
- **Medical Record Entity Endpoints**
- **Dashboard Endpoint**

## Development Setup

### Prerequisites

- Java 21
- Maven
- PostgreSQL
- Docker (optional for database setup)

### Running the Application

1. Clone the repository from GitHub.
2. Set up the PostgreSQL database.
3. Configure application properties for database access.
4. Run the application using Maven: `mvn spring-boot:run`.

## Contributing

Please follow the GitFlow pattern for branching and make sure to keep commits short and concise. For task management, refer to the project's Trello board.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
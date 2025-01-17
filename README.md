# Labmedical Project

# Overview
Labmedical is a backend REST API developed in Java, utilizing the Spring Boot framework. This project aims to provide a comprehensive system for managing patient records, consultations, and medical examinations.

# Application Requirements

- **Language and Framework**: The application is developed in Java, using Spring Boot.
- **Architecture**: Follows the MVC pattern with Controller, Service, and Repository layers.
- **Security**: Implements Spring Security for access control.
- **Data Transfer**: Utilizes DTOs for request and response handling.
- **Database**: Uses PostgreSQL for data storage, which can be set up via Docker or direct connection.
- **Documentation**: Generates API documentation using SpringDoc OpenAPI.
- **Automated Tests**:  Utilizes Cypress for automated end-to-end testing.


# Application Roadmap

# System Format

The application will include the following endpoints, secured with JWT tokens via Spring Security, except for the login endpoint which is unrestricted.

# Endpoints

- **Login Endpoint**
- **Registration Endpoint**
- **Patient Entity Endpoints**
- **Consultation Entity Endpoints**
- **Examination Entity Endpoints**
- **Medical Record Entity Endpoints**
- **Dashboard Endpoint**

# Development Setup

# Prerequisites

- Java 21
- Maven
- PostgreSQL
- Docker 
- Node.js and npm (for Cypress)


# Running the Application

1. Clone the repository from GitHub.
- git clone https://github.com/Felippeks/LABMedical-Backend.git
2. Set up the PostgreSQL database.
3. Configure application properties for database access.
4. Run the application using Maven: `mvn spring-boot:run`.

# Admin Credentials
The following admin credentials are pre-loaded in the database for testing:

### Email: admin@example.com
### Password: admin


# Cypress Automated Testing

## Installing Cypress
- Navigate to the project root directory.
- Install Cypress using npm: `npm install cypress --save-dev`.
- Install Faker.js using npm: `npm install @faker-js/faker`.

## Configuring Cypress
- Edit the cypress.config.json file to set the base URL for the application.
- Add or edit the following code to the cypress.json file:
```
async queryDatabase(query) {
          const client = new Client({
            host: 'localhost',
            user: 'user bd',
            password: 'password bd',
            database: 'database name',
            port: 'port number',

          });

          await client.connect();
          const res = await client.query(query);
          await client.end();
          return res.rows;
        }
```


## Running Cypress Tests
- Start the application.
- Run Cypress using npm: `npx cypress run`.
- Test files are organized into separate folders for each endpoint.

# Project Structure

Below is the structure of the Labmedical project:

```
+---src
|   +---main
|   |   +---java
|   |   |   \---br
|   |   |       \---com
|   |   |           \---senai
|   |   |               \---lab365
|   |   |                   \---labmedical
|   |   |                       |   LabmedicalApplication.java
|   |   |                       |
|   |   |                       +---config
|   |   |                       |   |   DataLoader.java
|   |   |                       |   |   JacksonConfig.java
|   |   |                       |   |   SecurityConfig.java
|   |   |                       |   |   SpringDocConfig.java
|   |   |                       |   |
|   |   |                       |   \---filtros
|   |   |                       |           JwtAuthenticationFilter.java
|   |   |                       |
|   |   |                       +---controllers
|   |   |                       |       ConsultaController.java
|   |   |                       |       DashboardController.java
|   |   |                       |       ExameController.java
|   |   |                       |       PacienteController.java
|   |   |                       |       UsuarioController.java
|   |   |                       |
|   |   |                       +---dtos
|   |   |                       |   +---cadastros
|   |   |                       |   |       CadastroRequestDTO.java
|   |   |                       |   |       CadastroResponseDTO.java
|   |   |                       |   |
|   |   |                       |   +---consultas
|   |   |                       |   |       ConsultaRequestDTO.java
|   |   |                       |   |       ConsultaResponseDTO.java
|   |   |                       |   |
|   |   |                       |   +---dashboard
|   |   |                       |   |       DashboardDTO.java
|   |   |                       |   |
|   |   |                       |   +---exames
|   |   |                       |   |       ExameRequestDTO.java
|   |   |                       |   |       ExameResponseDTO.java
|   |   |                       |   |
|   |   |                       |   +---login
|   |   |                       |   |       LoginRequestDTO.java
|   |   |                       |   |       LoginResponseDTO.java
|   |   |                       |   |
|   |   |                       |   +---paciente
|   |   |                       |   |       EnderecoDTO.java
|   |   |                       |   |       PacienteRequestDTO.java
|   |   |                       |   |       PacienteResponseDTO.java
|   |   |                       |   |
|   |   |                       |   \---prontuarios
|   |   |                       |           ProntuarioResponseDTO.java
|   |   |                       |
|   |   |                       +---entities
|   |   |                       |       ConsultaEntity.java
|   |   |                       |       Endereco.java
|   |   |                       |       ExameEntity.java
|   |   |                       |       PacienteEntity.java
|   |   |                       |       Perfil.java
|   |   |                       |       UsuarioEntity.java
|   |   |                       |
|   |   |                       +---exceptions
|   |   |                       |   |   GlobalExceptionHandler.java
|   |   |                       |   |
|   |   |                       |   +---consulta
|   |   |                       |   |       ConsultaNaoEncontradaException.java
|   |   |                       |   |
|   |   |                       |   +---exames
|   |   |                       |   |       BadRequestException.java
|   |   |                       |   |       ExameNaoEncontradoException.java
|   |   |                       |   |       ResourceNotFoundException.java
|   |   |                       |   |
|   |   |                       |   +---paciente
|   |   |                       |   |       CampoObrigatorioException.java
|   |   |                       |   |       CpfJaCadastradoException.java
|   |   |                       |   |       PacienteNaoEncontradoException.java
|   |   |                       |   |
|   |   |                       |   \---responses
|   |   |                       |           ApiResponseOK.java
|   |   |                       |
|   |   |                       +---repositories
|   |   |                       |       ConsultaRepository.java
|   |   |                       |       ExameRepository.java
|   |   |                       |       PacienteRepository.java
|   |   |                       |       UsuarioRepository.java
|   |   |                       |
|   |   |                       +---services
|   |   |                       |       AuthService.java
|   |   |                       |       ConsultaService.java
|   |   |                       |       DashboardService.java
|   |   |                       |       ExameService.java
|   |   |                       |       PacienteService.java
|   |   |                       |       UserDetailsServiceImpl.java
|   |   |                       |       UsuarioService.java
|   |   |                       |
|   |   |                       \---util
|   |   |                               JwtUtil.java
|   |   |                               ValidarCampoObrigatorio.java
|   |   |
|   |   \---resources
|   |       |   application.properties
|   |       |   private-key.pem
|   |       |   public-key.pem
```

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
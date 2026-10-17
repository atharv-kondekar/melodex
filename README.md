# Melodex — Music Library Management System

**A Spring Boot REST application featuring JWT-based authentication, album management, file uploads, DTO mapping, and centralized exception handling.**

Melodex is a Java backend development project designed to demonstrate the implementation of a structured, database-driven web application using Spring Boot. It provides functionality for managing users and albums through REST APIs, with a web interface built using HTML, CSS, and JavaScript.

The project focuses on practical backend engineering concepts, including layered architecture, secure password verification, token-based authentication, data transfer objects, file handling, and consistent API error responses.

> **Current scope:** Melodex supports music library and album management. Actual audio playback and music streaming are not currently implemented.

## Key Features

- **JWT Authentication** — Token-based authentication for accessing protected API endpoints.
- **Password Security** — BCrypt password hashing and verification.
- **User Management** — User-related operations through dedicated REST controllers and services.
- **Album Management** — Album operations organized into controller, service, DAO, and domain layers.
- **File Uploads** — Backend file-upload functionality integrated with the web interface.
- **ModelMapper Integration** — Mapping between domain objects and request/response DTOs.
- **Centralized Exception Handling** — Application-wide exception handling using `@RestControllerAdvice`.
- **Role-Based Authorization** — Role-based access restrictions where configured.
- **Layered Architecture** — Separation of request handling, business logic, and data access.
- **Web Interface** — HTML, CSS, and JavaScript modules for login, album management, uploads, and user management.
- **RESTful APIs** — HTTP-based communication between the frontend and backend.
- **Maven Build Management** — Dependency management and project build configuration.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend development |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Security | Authentication and authorization integration |
| JSON Web Tokens (JWT) | Token-based authentication |
| BCrypt | Password hashing and verification |
| ModelMapper | Entity and DTO mapping |
| JDBC / DAO | Database access |
| MySQL | Relational database |
| HTML5 | Frontend structure |
| CSS3 | Interface styling |
| JavaScript | Client-side functionality and API requests |
| Maven | Build and dependency management |
| Git and GitHub | Version control |

*The exact dependencies and versions are defined in `pom.xml`.*

## Application Architecture

Melodex follows a layered architecture that separates the major responsibilities of the application.

```text
                   Web Client
                       |
                 HTML / CSS / JS
                       |
                       v
                REST Controllers
                       |
                       v
                  Service Layer
                       |
                 +-----+-----+
                 |           |
                 v           v
             ModelMapper   File Handling
                 |
                 v
                DAO Layer
                 |
                 v
              MySQL Database
```

Authentication and request security are handled through the configured Spring Security filter chain.

```text
Client
  |
  | Login Credentials
  v
Authentication Service
  |
  | Validate BCrypt Password
  v
JWT Generation
  |
  | Bearer Token
  v
Spring Security Filter Chain
  |
  | Validate JWT and Apply Authorization
  v
Protected REST Endpoint
```

The exact request flow depends on the security configuration and individual endpoint implementations.

## Project Structure

```text
melodex/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sunbeam/
│   │   │       ├── AppConfig.java
│   │   │       ├── Sb20MusicRestApplication.java
│   │   │       │
│   │   │       ├── albums/
│   │   │       │   ├── controller/
│   │   │       │   │   └── AlbumController.java
│   │   │       │   ├── dao/
│   │   │       │   │   ├── AlbumDao.java
│   │   │       │   │   ├── AlbumDaoImpl.java
│   │   │       │   │   └── AlbumRowMapper.java
│   │   │       │   ├── domain/
│   │   │       │   │   └── Album.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── AlbumRequestDTO.java
│   │   │       │   │   └── AlbumResponseDTO.java
│   │   │       │   └── services/
│   │   │       │       └── AlbumService.java
│   │   │       │
│   │   │       └── users/
│   │   │           ├── controller/
│   │   │           │   └── UsersRestController.java
│   │   │           ├── dao/
│   │   │           │   ├── UserDao.java
│   │   │           │   ├── UserDaoImpl.java
│   │   │           │   └── UserRowMapper.java
│   │   │           ├── domain/
│   │   │           │   └── User.java
│   │   │           ├── dto/
│   │   │           │   ├── LoginCredentialsDTO.java
│   │   │           │   ├── UserRequestDTO.java
│   │   │           │   └── UserResponseDTO.java
│   │   │           ├── service/
│   │   │           │   └── UserService.java
│   │   │           └── utils/
│   │   │               ├── ApiResponse.java
│   │   │               ├── InvalidCredentialsException.java
│   │   │               └── MusicAppExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   │   ├── admin.js
│   │       │   │   ├── albums.js
│   │       │   │   ├── api.js
│   │       │   │   ├── login.js
│   │       │   │   ├── upload.js
│   │       │   │   └── users.js
│   │       │   ├── Admin.html
│   │       │   ├── Albums.html
│   │       │   ├── Login.html
│   │       │   ├── TestAlbums.html
│   │       │   ├── UploadAlbum.html
│   │       │   └── Users.html
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Authentication and Authorization

Melodex uses JWT-based authentication integrated with Spring Security.

The authentication workflow consists of:

1. Receiving login credentials from the client.
2. Retrieving the corresponding user record.
3. Verifying the submitted password against the stored BCrypt hash.
4. Generating a JWT after successful authentication.
5. Validating the token on subsequent protected requests.
6. Applying the configured authorization rules to determine access.

Example authorization header:

```http
Authorization: Bearer <JWT_TOKEN>
```

JWT authentication helps support stateless API access. Token expiration, signature validation, and authorization must be correctly configured for the application to be secure.

## File Uploads

The application includes file-upload functionality connected to the web interface and backend.

The upload process allows a client to submit a file to a backend endpoint for processing. Depending on the implementation, files may be stored on the server or in another configured storage location.

Important considerations for reliable file handling include:

- Validating file types and sizes.
- Handling invalid uploads and storage errors.
- Avoiding filename collisions and path traversal.
- Restricting access to uploaded content where necessary.
- Keeping uploaded files and sensitive data out of Git when appropriate.

## DTO Mapping with ModelMapper

Melodex uses ModelMapper to map between domain objects and DTOs.

For example:

```java
UserResponseDTO response =
        mapper.map(user, UserResponseDTO.class);
```

This reduces repetitive field-by-field mapping and separates internal domain models from the data exposed through API responses.

Request DTOs and response DTOs can also help define clear API contracts and prevent unnecessary exposure of internal entity fields.

## Centralized Exception Handling

Melodex uses Spring's `@RestControllerAdvice` for centralized exception handling.

Instead of duplicating error-handling logic across controllers, application exceptions can be handled centrally to produce consistent HTTP responses.

The project includes:

- `InvalidCredentialsException` — represents rejected login credentials.
- `MusicAppExceptionHandler` — provides centralized exception handling.
- `ApiResponse` — represents the application's API response structure.

A well-designed exception handler should return meaningful error messages and appropriate HTTP status codes without exposing stack traces, SQL details, or internal implementation information.

## Database Configuration

The application uses MySQL for relational data storage.

Configure the connection in:

`src/main/resources/application.properties`

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/melodex
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Adjust the database name and connection properties to match your local setup. Create the required schema and tables before running the application unless initialization scripts or application configuration create them automatically.

**Security:** Never commit real database credentials, JWT secrets, or other sensitive values to a public repository. Use environment variables or local configuration excluded through `.gitignore`.

## Getting Started

### Prerequisites

- A compatible JDK
- Maven or the included Maven Wrapper
- MySQL Server
- Git
- An IDE such as Spring Tools Suite or IntelliJ IDEA

### 1. Clone the repository

```bash
git clone https://github.com/atharv-kondekar/melodex.git
cd melodex
```

### 2. Configure the database

Create the configured database in MySQL and ensure the application has the necessary database permissions.

```sql
CREATE DATABASE melodex;
```

If your application expects an existing schema or initialization script, follow that setup instead.

### 3. Configure environment variables

Set the database username and password required by your configuration. Verify that the JDBC URL points to the correct database.

### 4. Build the application

On Windows:

```bash
mvnw.cmd clean package
```

Alternatively:

```bash
mvn clean package
```

### 5. Run the application

```bash
mvnw.cmd spring-boot:run
```

Alternatively:

```bash
mvn spring-boot:run
```

Open the application using the port configured in `application.properties`. For example, if the server uses port `8081`, visit `http://localhost:8081/`.

Use the actual controller mappings to determine the available API endpoints. Swagger UI may also be available if Springdoc is configured in the project.

## Future Improvements

Potential extensions for Melodex include:

- Audio playback and streaming.
- Improved automated tests for authentication and file uploads.
- More comprehensive input validation.
- API documentation and deployment configuration.
- Enhanced file storage and access controls.
- Improved frontend usability and error feedback.

These are potential improvements, not claims about existing functionality.

## Developer

**Atharv Kondekar**

B.Tech — Computer Science and Engineering (Artificial Intelligence and Machine Learning)

- GitHub: [atharv-kondekar](https://github.com/atharv-kondekar)
- Repository: [Melodex](https://github.com/atharv-kondekar/melodex)

---

*Melodex is a hands-on Java backend project exploring REST API development, authentication, data access, file handling, and maintainable application architecture.*

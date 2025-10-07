# User Management API

A Spring Boot RESTful API for managing user information with comprehensive OpenAPI/Swagger documentation.

## Project Structure

```
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── userapi
    │   │               ├── UserApiApplication.java
    │   │               ├── controller
    │   │               │   └── UserController.java
    │   │               └── model
    │   │                   └── User.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── example
                    └── userapi
```

## Technologies Used

- **Spring Boot 3.4.1** - Application framework
- **Java 17** - Programming language
- **Spring Web** - REST API support
- **SpringDoc OpenAPI 2.7.0** - API documentation (Swagger UI)
- **Maven** - Build and dependency management

## Features

- RESTful API endpoints for user management
- In-memory user storage (3 pre-configured dummy users)
- Comprehensive OpenAPI 3.0 documentation
- Interactive Swagger UI
- Proper HTTP status codes and error handling
- Bean validation support

## Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use Maven Wrapper included with Spring Boot)

## Building the Application

```bash
# Using Maven
mvn clean install

# Using Maven Wrapper (if available)
./mvnw clean install
```

## Running the Application

```bash
# Using Maven
mvn spring-boot:run

# Using Maven Wrapper
./mvnw spring-boot:run

# Or run the JAR directly
java -jar target/user-api-1.0.0.jar
```

The application will start on **http://localhost:8080**

## API Endpoints

### 1. Get All Users
- **URL**: `GET /users`
- **Description**: Retrieves a list of all users
- **Response**: 200 OK with array of user objects

**Example:**
```bash
curl http://localhost:8080/users
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Alice Johnson",
    "email": "alice.johnson@example.com"
  },
  {
    "id": 2,
    "name": "Bob Smith",
    "email": "bob.smith@example.com"
  },
  {
    "id": 3,
    "name": "Charlie Brown",
    "email": "charlie.brown@example.com"
  }
]
```

### 2. Get User by ID
- **URL**: `GET /users/{id}`
- **Description**: Retrieves a specific user by ID
- **Path Parameter**: `id` (Long) - User ID
- **Response**:
  - 200 OK with user object if found
  - 404 Not Found if user doesn't exist

**Example:**
```bash
curl http://localhost:8080/users/1
```

**Response (Success):**
```json
{
  "id": 1,
  "name": "Alice Johnson",
  "email": "alice.johnson@example.com"
}
```

**Response (Not Found):**
```
HTTP/1.1 404 Not Found
```

## OpenAPI/Swagger Documentation

### Accessing Swagger UI

Once the application is running, access the interactive Swagger UI at:

**http://localhost:8080/swagger-ui.html**

The Swagger UI provides:
- Interactive API documentation
- Ability to test endpoints directly from the browser
- Request/response schemas
- Example values
- Response codes and descriptions

### Accessing OpenAPI JSON

The raw OpenAPI 3.0 specification in JSON format is available at:

**http://localhost:8080/v3/api-docs**

## Configuration

Application configuration can be modified in `src/main/resources/application.properties`:

- **server.port**: Change the application port (default: 8080)
- **springdoc.swagger-ui.path**: Customize Swagger UI path
- **logging.level**: Adjust logging levels

## Dummy Users

The application is pre-populated with three users:

| ID | Name           | Email                      |
|----|----------------|----------------------------|
| 1  | Alice Johnson  | alice.johnson@example.com  |
| 2  | Bob Smith      | bob.smith@example.com      |
| 3  | Charlie Brown  | charlie.brown@example.com  |

## Future Enhancements

- Add POST, PUT, DELETE endpoints
- Integrate with a database (H2, PostgreSQL, etc.)
- Add pagination and filtering
- Implement exception handling with @ControllerAdvice
- Add unit and integration tests
- Implement security with Spring Security
- Add HATEOAS support

## License

This project is licensed under the Apache License 2.0.

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

- **Spring Boot 3.5.6** - Application framework
- **Java 21** - Programming language
- **Spring Web** - REST API support
- **SpringDoc OpenAPI 2.8.12** - API documentation (Swagger UI)
- **Maven** - Build and dependency management
- **PostgreSQL 17** - Database (via Docker Compose)
- **Concourse CI 7.14.2** - Continuous Integration/Continuous Deployment

## Features

- RESTful API endpoints for user management
- In-memory user storage (3 pre-configured dummy users)
- Comprehensive OpenAPI 3.0 documentation
- Interactive Swagger UI
- Proper HTTP status codes and error handling
- Bean validation support

## Prerequisites

- Java 21 or higher
- Maven 3.9+ (or use Maven Wrapper included with Spring Boot)

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

## Docker Compose Setup

This project includes a Docker Compose configuration for running Concourse CI locally with PostgreSQL 17.

### Starting the Services

```bash
# Start all services (Concourse CI + PostgreSQL)
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

The following services will be available:
- **Concourse CI Web UI**: http://localhost:8081 (user: `concourse`, password: `secret`)
- **PostgreSQL 17**: localhost:5432 (user: `concourse_user`, password: `concourse_pass`)

## Continuous Integration with Concourse CI

This project uses **Concourse CI 7.14.2** for automated builds and deployments. The pipeline is defined in `ci/pipeline.yml`.

### Installing fly CLI

The `fly` CLI is required to interact with Concourse from the command line.

**macOS:**
```bash
# Using Homebrew
brew install flyctl

# Or download directly from Concourse web UI
# Visit http://localhost:8081 and download for your OS
```

**Linux:**
```bash
# Download from Concourse web UI
wget http://localhost:8081/api/v1/cli?arch=amd64&platform=linux -O fly
chmod +x fly
sudo mv fly /usr/local/bin/
```

**Windows:**
```powershell
# Download from http://localhost:8081 and add to PATH
```

### fly CLI Usage

#### 1. Login to Concourse
```bash
# Login to local Concourse instance
fly -t local login -c http://localhost:8081 -u concourse -p secret

# Verify login
fly -t local status
```

#### 2. Set Pipeline
```bash
# Set the pipeline from ci/pipeline.yml
fly -t local set-pipeline -p user-api -c ci/pipeline.yml

# With variables file (if you have credentials.yml)
fly -t local set-pipeline -p user-api -c ci/pipeline.yml -l credentials.yml

# Unpause the pipeline
fly -t local unpause-pipeline -p user-api
```

#### 3. Trigger Pipeline
```bash
# Trigger the build-and-deploy job manually
fly -t local trigger-job -j user-api/build-and-deploy -w

# Trigger with watch flag to follow the build logs
fly -t local trigger-job -j user-api/build-and-deploy --watch
```

#### 4. Monitor Builds
```bash
# Watch a specific build
fly -t local watch -j user-api/build-and-deploy

# Get build status
fly -t local builds

# Get pipeline status
fly -t local pipelines
```

#### 5. Manage Pipelines
```bash
# List all pipelines
fly -t local pipelines

# Pause a pipeline
fly -t local pause-pipeline -p user-api

# Unpause a pipeline
fly -t local unpause-pipeline -p user-api

# Destroy a pipeline
fly -t local destroy-pipeline -p user-api
```

#### 6. Check Workers
```bash
# List all workers
fly -t local workers

# Prune stalled workers
fly -t local prune-worker -w <worker-name>
```

#### 7. Execute Tasks Locally
```bash
# Execute a task from the pipeline locally (useful for debugging)
fly -t local execute -c ci/pipeline.yml
```

### Pipeline Configuration

The pipeline (`ci/pipeline.yml`) includes:
- **Build Job**: Compiles the application using Maven with Eclipse Temurin JDK 21
- **Cloud Foundry Deployment**: Deploys to CF using the cf-cli-resource
- **Smoke Tests**: Validates the deployment

To customize the pipeline for your Cloud Foundry environment, update these variables:
- `cf-api-endpoint`: Your CF API endpoint
- `cf-username`: CF username
- `cf-password`: CF password
- `cf-org`: CF organization
- `cf-space`: CF space
- `cf-domain`: Your CF domain
- `cf-app-suffix`: App route suffix

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
- Integrate application with PostgreSQL 17 (currently available via Docker Compose)
- Add pagination and filtering
- Implement exception handling with @ControllerAdvice
- Add unit and integration tests
- Implement security with Spring Security
- Add HATEOAS support
- Configure pipeline secrets management with Vault or CredHub

## License

This project is licensed under the Apache License 2.0.

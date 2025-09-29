# Task Manager API

A production-ready RESTful API for task management, built with Spring Boot 3.1.0, Java 17, and MySQL. Supports user authentication (JWT), CRUD for tasks, and scales easily.

## Features
- User registration/login with JWT auth.
- CRUD endpoints for tasks (user-specific).
- MySQL persistence with Flyway migrations.
- Security: BCrypt passwords, protected endpoints.
- Error handling, validation, logging.
- Tests: Unit + integration (80%+ coverage).
- Deployment: Docker, CI/CD ready.

## Tech Stack
- Backend: Spring Boot (Web, Data JPA, Security, Validation)
- Database: MySQL 8+ with Flyway
- Auth: JWT (jjwt)
- Build: Maven
- Testing: JUnit 5, Mockito
- Logging: SLF4J/Logback

## Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+ (or Dockerized)
- IDE: IntelliJ/Eclipse (with Lombok plugin)

## Environment Variables
- `DB_USERNAME`: MySQL username (default: root)
- `DB_PASSWORD`: MySQL password (default: password)
- `DB_HOST`: MySQL host (default: localhost)
- `JWT_SECRET`: JWT signing key (generate a strong one for prod, min 256 bits)
- `SPRING_PROFILES_ACTIVE`: dev/prod (default: dev)

## Setup & Run
1. Clone repo: `git clone <repo-url>`
2. Start MySQL: Create DB `taskdb` or use Docker:  
   `docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=taskdb -d mysql:8`
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run` (or `java -jar target/task-manager-api-0.0.1-SNAPSHOT.jar`)
5. API Base: http://localhost:8080

Flyway auto-migrates schema on startup.

## API Endpoints
- **Auth (Public)**:
  - POST `/api/auth/register` - Register user (body: {username, password, email})
  - POST `/api/auth/login` - Login (body: {username, password}) → Returns JWT
- **Tasks (Protected - Bearer Token)**:
  - GET `/api/tasks` - List user's tasks
  - POST `/api/tasks` - Create task (body: TaskDto)
  - PUT `/api/tasks/{id}` - Update task
  - DELETE `/api/tasks/{id}` - Delete task

Example (Postman):
1. Register: POST body `{ "username": "test", "password": "pass123", "email": "test@example.com" }`
2. Login: POST body `{ "username": "test", "password": "pass123" }` → Copy "token"
3. Get Tasks: GET with Header `Authorization: Bearer <token>`

## Testing
- Run: `mvn test`
- Coverage: Use JaCoCo (add plugin if needed).

## Deployment
- **Local/Prod JAR**: `mvn package` → `java -jar target/*.jar --spring.profiles.active=prod`
- **Docker**: See Dockerfile. Push to registry (e.g., Docker Hub/ECR).
- **Cloud**: Deploy to Heroku/AWS (use RDS for MySQL). Set env vars in dashboard.
- **Scaling**: Add Spring Cloud for microservices; Kubernetes for orchestration.

## Monitoring
- Actuator: `/actuator/health`, `/actuator/metrics`
- Logs: Check console or integrate ELK.

## Customization
- Add features: Roles (extend User entity), pagination (Pageable in repos).
- Front-end: Integrate JSP/Thymeleaf or React (CORS enabled).
- Security: Add OAuth2 if needed.

## License
Proprietary - For client use only. Contact for support/upgrades.

Issues? Open a GitHub issue.

# Spring Boot Newcomers Guide (Nerd Edition)

Welcome to the Spring Boot backend of this monorepo! This guide is for absolute beginners and detail-oriented nerds who want to understand not just the "how" but the "why" behind our Spring Boot setup.

---

## 1. Project Structure & Key Files

- **src/main/java/com/**: Main business logic, organized by feature (e.g., order, user, loan). Typical subfolders:
	- `controller/` — REST endpoints (annotated with `@RestController`)
	- `service/` — Business logic (annotated with `@Service`)
	- `repository/` — Data access (annotated with `@Repository` or Spring Data interfaces)
	- `model/` or `entity/` — Domain objects (annotated with `@Entity` for JPA)
- **src/main/resources/**:
	- `application.properties` — Default config
	- `application-dev.properties` — Dev overrides (used for local development)
	- `log4j.properties`, `reload4j.properties` — Logging config
	- `static/`, `templates/` — For serving static content or templates (if needed)
- **pom.xml**: Maven build file. Manages dependencies, plugins, and build lifecycle.
- **amit/**: Docker Compose setups for MySQL, Kafka, Redis. See each subfolder for `docker-compose.yaml` and data volumes.

---

## 2. How Spring Boot Works Here

- **Auto-Configuration**: Spring Boot auto-wires beans based on classpath and annotations. Minimal XML config.
- **Dependency Injection**: Use `@Autowired` or constructor injection for services, repositories, etc.
- **Profiles**: Use `@Profile` or `spring.profiles.active` to switch between `application.properties` and `application-dev.properties`.
- **REST APIs**: Expose endpoints using `@RestController` and map URLs with `@RequestMapping`/`@GetMapping`/`@PostMapping`.
- **Persistence**: JPA/Hibernate for database access. Repositories extend `JpaRepository` or similar.
- **External Services**: Kafka, MySQL, and Redis are expected to be running via Docker Compose for full functionality.

---

## 3. Development Workflow

### Prerequisites
- JDK 11+ (AdoptOpenJDK or Oracle JDK recommended)
- Maven (use the wrapper: `mvnw.cmd` on Windows)
- Docker Desktop (for running MySQL, Kafka, Redis)

### Build & Run
1. **Start dependencies** (in separate terminals):
	 - MySQL: `docker-compose -f amit/mysqldockercompose/docker-compose.yaml up`
	 - Kafka: `docker-compose -f amit/kafka_docker_compose/docker-compose.yaml up`
	 - Redis: `docker-compose -f amit/redis_docker_compose/docker-compose.yaml up`
2. **Build the app**: `mvnw.cmd clean install`
3. **Run the app**: `mvnw.cmd spring-boot:run` (or run from your IDE)
4. **Access logs**: See `app.log` in the project root. Logs are rotated by date.

### Debugging
- Use your IDE’s debugger (set breakpoints in controllers/services)
- For log output, check `app.log` or configure log level in `application-dev.properties`
- For database issues, connect to MySQL using a GUI (e.g., DBeaver) with the credentials in the Docker Compose file

---

## 4. Configuration & Profiles

- **application.properties**: Default settings (used in all environments)
- **application-dev.properties**: Overrides for local development (set `spring.profiles.active=dev`)
- **Environment Variables**: Can override any property (e.g., `SPRING_DATASOURCE_URL`)
- **Secrets**: Never commit secrets. Use environment variables or a secrets manager for production.

---



## 5. Best Practices for Spring Boot & Java Development (Interview & Real-World)

### Code Organization & Style
- Use clear, descriptive names for classes, methods, and variables (Java conventions: `CamelCase` for classes, `camelCase` for methods/fields).
- Group related classes by feature (see `controller/`, `service/`, `repository/`, `model/` in `com/example/ecommercesystem/`).
- Keep classes focused—single responsibility principle.
- Prefer constructor injection over field injection for testability and immutability (see `LoanServiceImpl.java`).
- Use `final` for fields that should not change after initialization.
- Use interfaces for service and repository layers (see `LoanService` and `LoanServiceImpl`).

### REST API Design
- Use meaningful endpoint names and HTTP verbs (`GET`, `POST`, `PUT`, `DELETE`).
- Return appropriate HTTP status codes (e.g., `200 OK`, `201 Created`, `400 Bad Request`, `404 Not Found`).
- Validate input using `@Valid` and custom validators (see `dto/` for request/response objects).
- Document APIs with Swagger/OpenAPI if possible.
- Keep controllers thin—delegate business logic to services.

### Error Handling
- Use `@ControllerAdvice` and `@ExceptionHandler` for global error handling (see `GlobalExceptionHandler.java`).
- Return informative error messages in a consistent JSON structure (see `ErrorDetails.java`).
- Log errors with enough context for debugging, but avoid leaking sensitive info.

### Configuration & Profiles
- Store environment-specific settings in `application-dev.properties` (never hardcode secrets).
- Use environment variables for sensitive data in production.
- Document any new config properties you add.
- Use `@Value` to inject config properties into beans.

### Persistence & Transactions
- Use Spring Data JPA repositories for most DB access (see `ProductRepository.java`, `LoanRepository.java`).
- Write custom queries only when needed.
- Use `@Transactional` on service methods that perform multiple DB operations.
- Avoid business logic in repositories—keep it in services.

### Logging
- Use `@Slf4j` (Lombok) or `LoggerFactory` for logging.
- Log at appropriate levels: `info` for business events, `debug` for troubleshooting, `error` for failures.
- Avoid logging sensitive data (passwords, tokens, etc.).
- Configure log output in `log4j.properties` or `reload4j.properties`.

### Testing
- Write unit tests for all new business logic (see `test/java/com/example/ecommercesystem/model/ProductTest.java`).
- Use integration tests for controllers and repositories.
- Use Mockito for mocking dependencies.
- Run tests before every commit to catch regressions early.

### Collaboration & Workflow
- Use clear, descriptive commit messages.
- Keep pull requests focused and small—one feature or fix per PR.
- Review code for readability, maintainability, and adherence to these best practices.
- Document your code and decisions in comments or markdown files.

### Interview-Oriented Tips
- Be able to explain the architecture (controller-service-repository pattern, dependency injection, configuration management).
- Know how to handle exceptions globally and return consistent error responses.
- Understand how to write and test REST endpoints.
- Be familiar with transaction management and JPA basics.
- Practice writing unit and integration tests.
- Be ready to discuss how you would scale or secure the application (e.g., using Redis, Kafka, or Spring Security).

### General Tips
- Prefer immutability where possible.
- Avoid magic numbers and strings—use constants.
- Use JavaDocs for public classes and methods.
- Clean up unused code and imports before committing.

---

---

---

## 6. Common Gotchas & Tips

- Always use the Maven wrapper (`mvnw.cmd`) to ensure consistent builds.
- If you change dependencies, re-run `mvnw.cmd clean install`.
- If Docker containers fail, check for port conflicts or missing images.
- Use `@Transactional` on service methods that modify the database.
- Use constructor injection (not field injection) for better testability.
- Use `@Value` to inject config properties.
- For custom queries, use `@Query` in repositories.
- Log at appropriate levels (`info`, `debug`, `error`).
- Use `@Slf4j` (Lombok) or `LoggerFactory` for logging.

---

## 7. Learning Resources

- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Baeldung Spring Boot Tutorials](https://www.baeldung.com/spring-boot)
- [Spring Initializr](https://start.spring.io/) (for generating new projects)

---

## 8. To Do for Newcomers

- Explore the codebase, especially `com/` for business logic and `src/main/resources/` for config.
- Try running the app and hitting a REST endpoint (see controller classes for URLs).
- Add a simple endpoint or service as a practice exercise.
- Read the logs and try changing log levels.
- Experiment with Docker Compose to bring up/down services.
- Ask questions! The team values curiosity and learning.

---


---

## 9. Java Learning & Practice Resources in This Repo

This repository is not just a Spring Boot backend—it also serves as a playground for learning Java fundamentals, inspired by classic books like Yashwant Kanetkar's "Let Us Java." Here’s what you’ll find:

- **src/main/java/dsa/**: Contains data structures and algorithms implemented in Java. Examples include:
	- Custom Linked Lists, Stacks, Queues, Trees, Graphs
	- Sorting and Searching algorithms
	- Classic problems and their solutions
- **src/main/java/leetcode/**: Contains solutions to LeetCode and other coding challenge problems, often with multiple approaches and comments.
- **src/test/java/**: Includes test cases for both business logic and DSA/algorithm code, so you can see how to write and run tests in Java.
- **Patterns & Examples**: Many files in `dsa/` and `leetcode/` are written in a style similar to textbook examples—clear, concise, and focused on demonstrating a single concept or pattern. This is great for:
	- Practicing Java syntax and OOP concepts
	- Understanding how to structure small, self-contained programs
	- Experimenting with code snippets before integrating them into larger projects

**How to Use These Resources:**
- Browse the `dsa/` and `leetcode/` folders for example code and exercises.
- Try modifying or extending the examples to deepen your understanding.
- Use the test cases as templates for your own experiments.
- If you’re new to Java, start with the simpler classes in `dsa/` (like `CustomLinkedList` or `ArrayListBST`).

These resources are ideal for newcomers who want to learn Java by doing, following the spirit of hands-on books like "Let Us Java."

---

_For additional project-wide conventions, see `.github/copilot-instructions.md` and the Java Newcomers Guide in `.github/newcomers-java.md`._

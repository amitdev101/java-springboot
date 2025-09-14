# E-commerce System – Spring Boot Sample

This is an advanced, educational Spring Boot project modeling a simple e-commerce/loan system. It showcases best practices in JPA, validation, REST design, caching, async processing, and messaging, with detailed commentary to accelerate understanding.

---

## TL;DR
- Start deps: `docker-compose -f amit/mysqldockercompose/docker-compose.yaml up`, `-f amit/redis_docker_compose/docker-compose.yaml up`, `-f amit/kafka_docker_compose/docker-compose.yaml up`
- Run app: `mvnw.cmd spring-boot:run`
- Try: `GET http://localhost:8080/api/products/pagelist`
- Send Kafka msg: `POST /api/products/send-test-message` with `{ "message": "hello" }`
- Logs: `./logs/app.log` and console

## Project Structure

```
src/
└─ main/
   ├─ java/com/example/ecommercesystem/
   │  ├─ config/       ← Redis, Kafka, Security configuration
   │  ├─ controller/   ← REST endpoints (Products, Loans)
   │  ├─ service/      ← Business logic, caching, async processing
   │  ├─ repository/   ← Spring Data JPA interfaces
   │  ├─ model/        ← Entities with rich validation and mapping
   │  └─ kafka/        ← Test producer/consumer services
   └─ resources/
      ├─ application.properties      ← base config (DB, Redis, Kafka, logging)
      ├─ application-dev.properties  ← dev overrides & verbose logging
      └─ log4j.properties, reload4j.properties

amit/
├─ mysqldockercompose/      ← MySQL docker-compose + persisted volume
├─ redis_docker_compose/    ← Redis docker-compose + persisted volume
└─ kafka_docker_compose/    ← Kafka docker-compose + persisted volume

src/test/java/              ← JUnit 5 tests (model + context)
```

---

## Getting Started

### Prerequisites
- JDK 8+ (JDK 11 recommended)
- Maven (or use wrapper `mvnw.cmd` on Windows)
- Docker Desktop (for MySQL, Redis, Kafka)

### Start Local Dependencies (Docker)
Run from repo root in separate terminals:
- MySQL: `docker-compose -f amit/mysqldockercompose/docker-compose.yaml up`
- Redis: `docker-compose -f amit/redis_docker_compose/docker-compose.yaml up`
- Kafka: `docker-compose -f amit/kafka_docker_compose/docker-compose.yaml up`

### Build & Run
```
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```
App starts at `http://localhost:8080`

---

## Feature Highlights

### Entity Modeling
- `Product`, `Category`, `Loan` with extensive validation annotations (`@NotBlank`, `@Size`, `@Email`, `@Future`, `@PastOrPresent`, numeric bounds)
- Relationship mapping with clear guidance on cascade and fetch strategies
- JSON serialization control to avoid cycles (`@JsonManagedReference`, `@JsonIgnore`)

### Repository Layer
- Spring Data JPA derived queries and custom JPQL with `@Query`
- Pagination and sorting via `Pageable`

### Service Layer, Caching, and Async
- Redis-backed Spring Cache (`@Cacheable`, `@CachePut`, `@CacheEvict`) for product list and product-by-id
- ExecutorService + `Future` + `CompletableFuture` patterns for concurrent processing

### REST & Validation
- Product and Loan controllers with `@Valid` at boundaries
- Centralized error handling via `@ControllerAdvice` with consistent response shape

### Messaging (Kafka)
- Simple producer/consumer wired with String serdes (`test_topic`)
- Test endpoint to publish messages

### Observability
- Spring Boot Actuator endpoints exposed for development

---

## cURL Cheatsheet
- List products: `curl http://localhost:8080/api/products`
- Paged products: `curl "http://localhost:8080/api/products/pagelist?page=0&size=12"`
- Get product by id: `curl http://localhost:8080/api/products/1`
- Search products: `curl "http://localhost:8080/api/products/search?name=Laptop&description=gaming"`
- Create product:
  `curl -X POST http://localhost:8080/api/products -H "Content-Type: application/json" -d '{"name":"Laptop","description":"Gaming","price":1299.99,"contactEmail":"support@example.com","quantityAvailable":10}'`
- Fire-and-forget processing: `curl -X POST http://localhost:8080/api/products/process`
- Futures result: `curl -X POST http://localhost:8080/api/products/process-async-with-futures`
- CompletableFuture result: `curl -X POST http://localhost:8080/api/products/process-async-with-cf`
- Kafka test: `curl -X POST http://localhost:8080/api/products/send-test-message -H "Content-Type: application/json" -d '{"message":"hello"}'`

---

## API Endpoints (Selected)
- Products
  - `GET /api/products` – list all (cached)
  - `GET /api/products/pagelist?page=&size=` – paged listing
  - `GET /api/products/{id}` – get by id
  - `GET /api/products/search?name=...&description=...`
  - `POST /api/products` – create (validated)
  - Async demos: `POST /api/products/process`, `/process-async-with-futures`, `/process-async-with-cf`
  - Kafka test: `POST /api/products/send-test-message` with `{ "message": "hello" }`
- Loans
  - `GET /loans` | `GET /loans/{id}` | `POST /loans` | `PUT /loans/{id}` | `DELETE /loans/{id}`
- Actuator
  - `GET /actuator`

---

## Configuration
- MySQL: `jdbc:mysql://0.0.0.0:3306/loan_management` (`mysqluser`/`mysqlpass`)
- Redis: `localhost:6379`
- Kafka bootstrap: `localhost:9092`
- Active profile: `dev`

---

## Configuration Deep Dive
- Profiles
  - Active: `dev` (see `application-dev.properties`)
  - Override via env: `SPRING_PROFILES_ACTIVE=dev`
- Database (MySQL)
  - URL: `spring.datasource.url=jdbc:mysql://0.0.0.0:3306/loan_management`
  - User/Pass: `spring.datasource.username=mysqluser`, `spring.datasource.password=mysqlpass`
  - DDL: `spring.jpa.hibernate.ddl-auto=update` (use `validate`/migrations for prod)
- Redis Cache
  - Host/Port: `spring.redis.host=localhost`, `spring.redis.port=6379`
  - Cache type: `spring.cache.type=redis`, TTL: `spring.cache.redis.time-to-live=600000`
- Kafka
  - `spring.kafka.bootstrap-servers=localhost:9092`, consumer group `myGroup`
- Logging
  - Root level: `INFO`; file output to `./logs/app.log`

Environment overrides (PowerShell):
```
$env:SPRING_PROFILES_ACTIVE="dev"
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/loan_management"
$env:SPRING_DATASOURCE_USERNAME="mysqluser"
$env:SPRING_DATASOURCE_PASSWORD="mysqlpass"
mvnw.cmd spring-boot:run
```

---

## Database Notes
- Entities: `Product`, `Category`, `Loan`
- Relationships: `Product` → `Category` many-to-one; `Category.products` one-to-many with orphan removal
- Batch inserts: `GenerationType.IDENTITY` impacts JDBC batching; prefer sequences for bulk loads
- Suggested indexes: `product(name)`, `product(price)`, `category(name)`

---

## Caching Details
- Cache names: `products` (list), `product` (by id)
- Keys: `product::<id>`, `products::SimpleKey []`
- Invalidation: `saveProduct` evicts all `products` and updates `product::<id>`
- Serialization: `GenericJackson2JsonRedisSerializer`

---

## Kafka How-To
- Start broker via docker-compose (topic auto-create enabled)
- Produce via API: `POST /api/products/send-test-message` with `{ "message": "hi" }`
- Observe logs: consumer prints `Received test_topic Message: ...` in console

---

## Testing
- JUnit 5 tests under `src/test/java` (model tests, context loads)

Handy commands
- Run all tests: `mvnw.cmd test`
- Skip tests on build: `mvnw.cmd -DskipTests clean install`
- Single test: `mvnw.cmd -Dtest=ProductTest test`

---

## Why This Project Exists
To serve as a live textbook for Spring Boot, JPA, caching, async, and messaging—bridging theory and production-grade code in one resource. Useful for:
- Self-learners and interview prep
- Bootcamp/instructor demos
- Team onboarding and mentorship

---

## Error Model & Validation
- Validation failures (HTTP 400):
```
{
  "error": "validation_failed",
  "fields": {
    "name": "Product name cannot be blank"
  }
}
```
- Not found (HTTP 404): JSON with `timestamp`, `message`, `details`

---

## Pagination & Sorting
- Default page size: 12; default sort: `id,asc`
- Request format: `GET /api/products/pagelist?page=0&size=12&sort=id,asc`
- Response provides `content`, `totalElements`, `totalPages`, `number`, etc.

---

## Roadmap / TODO (Inline)

### Implemented
- Redis cache integration for products (Spring Cache with Redis)
- Logging setup (SLF4J + log4j/reload4j), file + console
- JUnit 5 tests scaffold (model + context)
- Kafka basics (producer/consumer) and test endpoint
- Product pagination endpoint (`/api/products/pagelist`)
- Async processing (ExecutorService, Futures, CompletableFuture)
- MySQL via Spring Data JPA (entities, repositories)
- Actuator endpoints enabled in dev
- Dev profile and overrides
- Docker Compose for MySQL, Redis, Kafka

### Planned
- Fix/verify serialization infinite loops in bidirectional entities
- Kafka concurrent processing patterns and tuning
- Connection pool sizing and thread configuration
- Custom decorator example
- Refine profile-specific configuration beyond `dev`
- WebSocket sample implementation
- Transactional boundaries and DB-level locking demos
- MongoDB setup and integration
- Harden Spring Security (currently permissive for development)

---

## Contributions & License
Contributions that enhance educational value are welcome.

License: MIT (if a LICENSE file is present in the repo).

---

Happy learning—and may this be the forge of your architectural mastery.

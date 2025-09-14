<!-- PROJECT LOGO -->
<p align="center">
  <a href="#">
    <img src="https://raw.githubusercontent.com/amitdev101/java-springboot/main/docs/logo.png" alt="Project Logo" width="120" height="120">
  </a>
</p>

<h1 align="center">E-Commerce / Loan Management System - Spring Boot</h1>

<p align="center">
  <i>A comprehensive Spring Boot learning project covering JPA, REST, Validation, Caching, Kafka, Async, and more.</i>
</p>

<p align="center">
  <!-- Badges -->
  <img src="https://img.shields.io/badge/Java-11%2B-red?style=for-the-badge&logo=java" alt="Java">
  <img src="https://img.shields.io/badge/SpringBoot-3.x-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Build-Maven-blue?style=for-the-badge&logo=apachemaven" alt="Maven">
  <img src="https://img.shields.io/badge/Database-MySQL-orange?style=for-the-badge&logo=mysql" alt="MySQL">
  <img src="https://img.shields.io/badge/Cache-Redis-critical?style=for-the-badge&logo=redis" alt="Redis">
  <img src="https://img.shields.io/badge/Messaging-Kafka-black?style=for-the-badge&logo=apachekafka" alt="Kafka">
  <img src="https://img.shields.io/github/license/amitdev101/java-springboot?style=for-the-badge" alt="License">
</p>

---

## 📑 Table of Contents

1. [About the Project](#about-the-project)
2. [Tech Stack](#tech-stack)
3. [Project Structure](#project-structure)
4. [Getting Started](#getting-started)
5. [Feature Highlights](#feature-highlights)
6. [API Endpoints & cURL](#api-endpoints--curl)
7. [Configuration](#configuration)
8. [Database & Caching](#database--caching)
9. [Testing](#testing)
10. [Roadmap](#roadmap)
11. [Contributions & License](#contributions--license)

---

## 📘 About the Project

This project serves as a reference architecture for a modern Spring Boot application.
It simulates an e-commerce + loan management system with realistic concerns:

- Entity relationships with validation
- Database persistence (MySQL)
- Performance tuning via Redis caching
- Async and concurrent workflows
- Distributed communication using Kafka
- Robust configuration across environments
- Developer productivity with Dockerized infrastructure

> The intent is educational depth - so learners see not only how things work, but how they interlock in a production-like environment.

---

## 🛠️ Tech Stack

- Language: Java 11+
- Framework: Spring Boot (Web, Data JPA, Validation, Cache, Actuator)
- Database: MySQL
- Cache: Redis
- Messaging: Apache Kafka
- Build Tool: Maven
- Containerization: Docker & Docker Compose
- Logging: Log4j + reload4j
- Testing: JUnit 5, Spring Boot Test

---

## 📁 Project Structure

```
src/
  main/java/com/example/ecommercesystem/
    config/        # Redis, Kafka, Logging, Security
    controller/    # REST endpoints
    service/       # Business logic, caching, async
    repository/    # Spring Data JPA repositories
    model/         # Entities, validation, serialization
    kafka/         # Producer & Consumer services
  main/resources/
    application.properties
    application-dev.properties
    log4j.properties
    reload4j.properties

amit/              # Infrastructure scripts
  mysqldockercompose/      # MySQL docker-compose
  redis_docker_compose/    # Redis docker-compose
  kafka_docker_compose/    # Kafka docker-compose

test/
  java/            # JUnit 5 tests
```

---

## 🚀 Getting Started

### Prerequisites
- Java 11+ (Java 17 compatible)
- Maven (or use `./mvnw`)
- Docker & Docker Compose

### Setup & Run Locally

1) Start dependencies (run in separate terminals):

```bash
docker-compose -f amit/mysqldockercompose/docker-compose.yaml up
docker-compose -f amit/redis_docker_compose/docker-compose.yaml up
docker-compose -f amit/kafka_docker_compose/docker-compose.yaml up
```

2) Build and run the app:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

3) Access the app:

http://localhost:8080

---

## ✨ Feature Highlights

- Entity modeling with advanced javax.validation constraints
- REST API design with validation and unified error handling
- Repository layer powered by Spring Data JPA (custom queries, pagination, sorting)
- Service layer with Redis caching (`@Cacheable`, `@CacheEvict`)
- Async workflows using ExecutorService, Futures, and CompletableFuture
- Kafka messaging with producer/consumer scaffolding
- Observability with Spring Actuator, console + file logging

---

## 🔌 API Endpoints & cURL

Products

```bash
# Get all products
curl -X GET http://localhost:8080/api/products

# Get paged products
curl -X GET "http://localhost:8080/api/products/pagelist?page=0&size=12"

# Get by ID
curl -X GET http://localhost:8080/api/products/1

# Search
curl -X GET "http://localhost:8080/api/products/search?name=Laptop&description=gaming"

# Create product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{ "name":"Laptop","description":"Gaming","price":1299.99,"contactEmail":"support@example.com","quantityAvailable":10 }'
```

Async

```bash
curl -X POST http://localhost:8080/api/products/process
curl -X POST http://localhost:8080/api/products/process-async-with-futures
curl -X POST http://localhost:8080/api/products/process-async-with-cf
```

Kafka

```bash
curl -X POST http://localhost:8080/api/products/send-test-message \
  -H "Content-Type: application/json" \
  -d '{ "message":"hello" }'
```

---

## ⚙️ Configuration

- MySQL: `jdbc:mysql://0.0.0.0:3306/loan_management` (user: `mysqluser`, pass: `mysqlpass`)
- Redis: host `localhost`, port `6379`
- Kafka: `localhost:9092`, consumer group `myGroup`
- Profiles: `dev` by default; switch via `SPRING_PROFILES_ACTIVE`
- Logging: Console + `./logs/app.log`

Environment override example (PowerShell):

```powershell
$env:SPRING_PROFILES_ACTIVE = "dev"
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/loan_management"
$env:SPRING_DATASOURCE_USERNAME = "mysqluser"
$env:SPRING_DATASOURCE_PASSWORD = "mysqlpass"
./mvnw spring-boot:run
```

---

## 🗄️ Database & Caching

- Entities: `Product`, `Category`, `Loan`
- Relationships: One-to-Many, Many-to-One with cascading
- Indexes: `product(name)`, `product(price)`, `category(name)`
- Batching: Consider effects of `GenerationType.IDENTITY`
- Caching (Redis):
  - Cache names: `products`, `product`
  - Keys: `product::id`, `products::SimpleKey[]`
  - Eviction: Auto on save/update
  - Serialization: JSON with `GenericJackson2JsonRedisSerializer`

---

## 🧪 Testing

Run all tests:

```bash
./mvnw test
```

Run a specific test:

```bash
./mvnw -Dtest=ProductTest test
```

Skip tests during build:

```bash
./mvnw -DskipTests clean install
```

---

## 🗺️ Roadmap

- Redis caching
- Kafka producer/consumer
- REST endpoints & pagination
- Async workflows
- Dockerized infrastructure
- JUnit scaffolding
- Dev profile configs

Planned:

- WebSocket integration
- Security (roles & JWT auth)
- MongoDB integration
- Connection pooling improvements
- Decorator patterns
- DB migrations instead of Hibernate DDL

---

## 🤝 Contributions & License

Contributions are welcome. Please fork and submit pull requests for improvements.

License: MIT (add a LICENSE file if missing)

<p align="center"><i>Happy learning - may this repository be your forge of architectural mastery.</i></p>




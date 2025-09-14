# TODO and Implemented

This file groups planned work and completed items for quick tracking.

## Implemented ✅
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

## Planned 🚧
- Fix/verify serialization infinite loops in bidirectional entities
- Kafka concurrent processing patterns and tuning
- Connection pool sizing and thread configuration
- Custom decorator example
- Refine profile-specific configuration beyond `dev`
- WebSocket sample implementation
- Transactional boundaries and DB-level locking demos
- MongoDB setup and integration
- Harden Spring Security (currently permissive for development)

Notes
- Original checklist also lives in `TODO` (root). This markdown view consolidates it with status.

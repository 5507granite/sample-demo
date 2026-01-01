# Microservice Sample: User, Order, Payment

This is a basic sample microservice using Java and Spring Boot for demonstration purposes.

## Structure

- **Controller** (`controller/`): API layer - handles HTTP requests.
- **Service** (`service/`): Business logic layer.
- **DTO** (`dto/`): Data transfer objects, used for API request/responses.
- **Entity** (`entity/`): Database entity mappings.
- **Repository** (`repository/`): Database access layer.
- **Exception** (`exception/`): Custom exceptions and handlers.

## How to Run

1. Ensure you have Java 11+ and Maven.
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access sample endpoints:
   - [GET] `/api/users`
   - [POST] `/api/users`
   - [GET] `/api/orders`
   - [POST] `/api/orders`
   - [GET] `/api/payments`
   - [POST] `/api/payments`
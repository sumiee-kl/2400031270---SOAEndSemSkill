# Consumer GET Service

A contract-first Spring Boot microservice that exposes a single GET endpoint. The OpenAPI YAML contract is the source of truth: the Java API interface and response models are generated from it at build time, so the code cannot drift from what the consumer agreed to.

## Contract (`src/main/resources/openapi/contract.yaml`)

| Step | What it defines |
|------|-----------------|
| Step 1: Create contract file | OpenAPI 3.0.3 document, info, servers |
| Step 2: Define GET request structure | `GET /api/v1/consumer` with required query params `a` and `b` (strings) |
| Step 3: Define response spec | `200 OK` with `ConsumerResponse` schema |

### Request

```
GET /api/v1/consumer?a=5&b=10
```

### Response: 200 OK

```json
{
  "status": 200,
  "message": "SUCCESS",
  "data": {
    "a": "5",
    "b": "10"
  }
}
```

If `a` or `b` is missing, the service returns `400 Bad Request` (Spring's standard behaviour for required parameters).

## Tech stack

- Java 17, Spring Boot 3.3
- OpenAPI Generator Maven plugin (interface-only Spring generator)
- springdoc-openapi (Swagger UI)
- JUnit 5 + MockMvc

## Project structure

```
consumer-get-service/
├── pom.xml
├── src/main/java/com/example/consumer/
│   ├── ConsumerGetServiceApplication.java
│   ├── controller/ConsumerController.java   # implements generated ConsumerApi
│   ├── service/ConsumerService.java          # builds the 200 response
│   └── exception/GlobalExceptionHandler.java # 400 on missing params
├── src/main/resources/
│   ├── application.yml
│   └── openapi/contract.yaml                 # THE CONTRACT
└── src/test/java/com/example/consumer/controller/ConsumerControllerTest.java
```

Generated code (`ConsumerApi`, `ConsumerResponse`, `ConsumerData`) lands in `target/generated-sources/openapi` and is not committed.

## Run

```bash
mvn clean verify          # generates code from the contract, compiles, runs tests
mvn spring-boot:run       # starts on http://localhost:8080
```

Try it:

```bash
curl "http://localhost:8080/api/v1/consumer?a=5&b=10"
```

- Swagger UI: http://localhost:8080/swagger-ui.html
- Raw contract: http://localhost:8080/openapi/contract.yaml
- Health: http://localhost:8080/actuator/health

## Changing the contract

1. Edit `contract.yaml`.
2. Run `mvn clean compile`. The interface and models regenerate.
3. Fix any compile errors in `ConsumerController` / `ConsumerService`. Those errors are the contract enforcing itself.

## Push to GitHub

```bash
git init
git add .
git commit -m "Initial commit: contract-first consumer GET service"
git branch -M main
git remote add origin https://github.com/<your-username>/consumer-get-service.git
git push -u origin main
```

# HDFC Life Policy Desk API

A simple Spring Boot REST API for managing HDFC Life policies and claims. Policies are stored in memory and Flyway manages the database schema.

## Run

```bash
./mvnw spring-boot:run
```

App: `http://localhost:8080`

Swagger: `http://localhost:8080/swagger-ui/index.html`

OpenAPI: `http://localhost:8080/v3/api-docs`

## Endpoints

| Method | Path                              | Status        |
| ------ | --------------------------------- | ------------- |
| GET    | `/api/policies`                   | 200           |
| GET    | `/api/policies/{policyNo}`        | 200, 404      |
| GET    | `/api/policies?status=Active`     | 200           |
| GET    | `/api/policies?type=TERM`         | 200           |
| POST   | `/api/policies`                   | 201, 409      |
| PUT    | `/api/policies/{policyNo}`        | 200, 404      |
| DELETE | `/api/policies/{policyNo}`        | 204, 404      |
| GET    | `/api/policies/{policyNo}/claims` | 200, 404      |
| POST   | `/api/claims`                     | 201, 400, 404 |
| GET    | `/api/claims/{claimNo}`           | 200, 404      |

## Database

Flyway manages these 5 tables:

* `customers`
* `policies`
* `claims`
* `riders`
* `policy_riders`

Relationships:

```text
policies.customer_id → customers.id
claims.policy_id → policies.id
policy_riders.policy_id → policies.id
policy_riders.rider_id → riders.id
```

## In-Memory vs PostgreSQL

In-memory storage is good for testing and simple development. PostgreSQL is better when data needs to stay after restarting the app or be shared between users. Flyway keeps database changes versioned and controlled, unlike `ddl-auto=update`, which changes the schema automatically.

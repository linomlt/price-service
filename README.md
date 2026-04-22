# Price Service

This project is a Spring Boot application that provides a REST API to query the applicable price for a specific product,
brand, and date.

## Technologies

- Java 21
- Maven
- Spring Boot 3.5
- Spring Data JPA
- H2 Database (In-memory)
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Architecture

The project follows a Hexagonal Architecture (Ports and Adapters) pattern, as described in the book "Get Your Hands
Dirty on Clean Architecture" by Tom Hombergs ([buckpal repository](https://github.com/thombergs/buckpal)). This approach
decouples the domain logic from external concerns like the web layer or the database.

- **Domain**: Business entities and value objects (Price, Money, DateRange)
- **Application**: Ports (interfaces) and Services (use cases)
- **Adapters**:
    - **In**: REST Controller
    - **Out**: Persistence with JPA

```text
io.github.linomlt.priceservice
├── domain
│   ├── exception           (Domain-specific Exceptions)
│   └── model               (Domain Entities and Value Objects)
├── application
│   ├── port
│   │   ├── in              (Input Ports, Use Case Interfaces)
│   │   └── out             (Output Ports, Persistence Interfaces)
│   └── service             (Application Services, Implementation of Use Cases)
├── adapter
│   ├── in
│   │   └── rest            (REST Controller, Global Exception Handler, DTOs)
│   └── out
│       └── persistence     (JPA Entities, Repositories, Mappers)
└── config           
```

## Requirements

To build and run this project, you need:

- JDK 21
- Maven (or use the provided `./mvnw` wrapper)

## Running the Application

You can run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Documentation

Once the application is running, you can access the Swagger UI to explore and test the API:

`http://localhost:8080/swagger-ui.html`

## Endpoints

### Get Applicable Price

Returns the price with the highest priority for a given brand, product, and application date.

- **URL**: `/api/v1/prices/applicable`
- **Method**: `GET`
- **Parameters**:
    - `brandId` (Long): Brand identifier.
    - `productId` (Long): Product identifier.
    - `applicationDate` (LocalDateTime): Application date-time in ISO-8601 format (e.g., `2020-06-14T10:00:00`).

- **Response Example**:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

## Testing

The project includes a comprehensive test suite covering different layers of the architecture:

- **Unit tests of domain model**: Validate the business logic and constraints within the domain entities (`PriceTest`,
  `MoneyTest`, `DateRangeTest`).
- **Unit tests of use cases**: Test the application services in isolation by mocking the output ports (
  `GetApplicablePriceServiceTest`).
- **Integration tests of persistence adapter**: Verify the integration with the H2 database and the correctness of the
  JPA queries (`PricePersistenceAdapterTest`).
- **Integration tests of REST adapter**: Ensure the REST endpoints are correctly mapped and handle requests/responses as
  expected (`PriceControllerTest`).
- **System tests**: End-to-end tests that validate the entire flow of the application from the REST API to the
  database (`GetApplicablePriceSystemTest`).

## Running Tests

To run the unit and integration tests, execute:

```bash
./mvnw test
```

The tests cover various scenarios, including the specific cases requested for the technical challenge.

## Data Initialization

The application initializes an H2 in-memory database with some sample data on startup (see `DataInitializer.java`),
representing the different price rates for product 35455 and brand 1 across various date ranges. This initialized data
is used for both **System tests** and **Integration tests of REST adapter** to ensure consistent verification of the
technical challenge scenarios.

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14-00.00.00 | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14-15.00.00 | 2020-06-14-18.30.00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15-00.00.00 | 2020-06-15-11.00.00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15-16.00.00 | 2020-12-31-23.59.59 | 4          | 35455      | 1        | 38.95 | EUR  |

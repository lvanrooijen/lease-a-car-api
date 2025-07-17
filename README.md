# lease-a-car-api

Originally created as a take-home assessment, now extended into a personal (overengineered) learning project.

## Technical Specifications

* Spring Boot REST API with full CRUD functionality
* Role-based access control using Spring Security
* Soft deletes via Hibernate @SQLDelete
* CSV import functionality using OpenCSV
* DTO mapping, exception handling, and input validation
* documentation with OpenAPI (Swagger)
* Application event system for user registration flow
* Email templates created with Thymeleaf
* PostgreSQL database running in Docker
* PG admin for database management via Docker
* MailDev for local email testing via Docker

## Requirements

- Java 17+
- Maven

## How to run

1. Clone the project:

             git clone https://github.com/lvanrooijen/lease-a-car-api.git

2. `docker compose up --build`
3. `mvn install`
4. `mvn spring-boot:run` or 
   [![image](https://github.com/user-attachments/assets/c448a1c6-24f2-438a-9763-df3f5765c057)](https://www.youtube.com/watch?v=MtaTKXJ89jk)

The API runs locally, services run via docker

## Where to find:

* The API will be running on http://localhost:8080
* MailDev runs on http://localhost:1080
* PG admin runs on http://localhost:5050 (register server, hostname: lease_a_car_postgres)

## Structure & Design

### This project contains the following directories:

1. `events/` contains events and related classes
2. `email/` contains email related classes
3. `exception/` contains custom exceptions and a global exception handler
4. `security/` contains Spring Security related classes
5. `entity/` contains:

  - `Entity` : Represents the entity/model (Car, Customer)

  - `Controller` : Controller class for handling HTTP requests

  - `Service` : A Service class that handles the business logic related to the entity

  - `Repository` : Handles querying the database

      - `DTO/` : contains DTOs I often use:
          - `[name of entity]Mapper`: Class that handles mapping the dto to entity, entity to dto and patching the entity.
          - `Get[name of entity]`: What is returned to the controller. Contains a factory method `to()` to convert the entity
            to this DTO.
          - `Post[name of entity]`: Used as a request body when posting a new entity. Occasionally contains a factory method
            `from()` to convert the DTO to an entity.
          - `Patch[name of entity]`: Used as a request body for patching an entity.

6. `utils/` contains the following directories:

    - `annotations/` : contains custom annotations for validation, primarily used for validating values in request bodies
      through DTOs.
    - `constants/` : contains classes that hold constant values. (Endpoints, ExceptionMessages)
    - `configuration/` : contains classes that hold configurations

## Seeded Users

Users for testing have been seeded, you can login with the following credentials:

1. **Broker**:

    - **Username**: `broker@email.com`
    - **Password**: `SecurePassword123!`
    - **Role**: `ROLE_BROKER`

2. **Employee**:

    - **Username**: `employee@email.com`
    - **Password**: `SecurePassword123!`
    - **Role**: `ROLE_EMPLOYEE`

3. **Admin**:
    - **Username**: `admin@email.com`
    - **Password**: `SecurePassword123!`
    - **Role**: `ROLE_ADMIN`

## Sources i used to build this project:

- [soft delete](https://www.baeldung.com/spring-jpa-soft-delete)
- [parameterized tests](https://www.baeldung.com/parameterized-tests-junit-5)
- [open csv](https://www.youtube.com/watch?v=1jzGHF8bpn0)
- [spring security - wip](https://www.manning.com/books/spring-security-in-action-second-edition)
- [spring events](https://www.baeldung.com/spring-events)
- [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html)
- [4 common pitfalls BigDecimal](https://blogs.oracle.com/javamagazine/post/four-common-pitfalls-of-the-bigdecimal-class-and-how-to-avoid-them)
- [clean code](https://www.baeldung.com/java-clean-code)
- [exception handling](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [spring-email](https://www.baeldung.com/spring-email)

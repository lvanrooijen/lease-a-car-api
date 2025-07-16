# lease-a-car-api

Originally created as a take-home assessment for SVB, now extended into a personal (overengineered) learning project.

## Requirements

- Java 17+
- Maven

## How to run

1. Clone the project:

             git clone https://github.com/lvanrooijen/lease-a-car-api.git

2. `mvn install`
3. `mvn spring-boot:run`

    - The API will be running on http://localhost:8080

## Swagger documentation:

Swagger Documentation here: http://localhost:8080/swagger-ui/index.html

## Structure & Design

### _This project contains the following directories:_

#### _`events` folder containing events and related classes_

#### _`exception` folder containing custom exceptions and a global exception handler_

#### _`security` folder for Spring Security related classes_

#### _`entity` folder which contains:_

- `Entity` : Represents the entity/model (Car, Customer)

- `Controller` : Controller class for handling HTTP requests

- `Service` : A Service class that handles the business logic related to the entity

- `Repository` : Handles querying the database

- `DTO Folder` : Folder containing DTOs I often use:
    - `[name of entity]Mapper`: Class that handles mapping the dto to entity, entity to dto and patching the entity.
    - `Get[name of entity]`: What is returned to the controller. Contains a factory method `to()` to convert the entity
      to this DTO.
    - `Post[name of entity]`: Used as a request body when posting a new entity. Occasionally contains a factory method
      `from()` to convert the DTO to an entity.
    - `Patch[name of entity]`: Used as a request body for patching an entity.

#### _`utils` folder containing the following folders:_

- `annotations` : Contains custom annotations for validation, primarily used for validating values in request bodies
  through DTOs.
- `constants` : contains classes that hold constant values. (Endpoints, ExceptionMessages)
- `configuration` : contains classes that hold configurations

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

## Learning sources:

- [soft delete](https://www.baeldung.com/spring-jpa-soft-delete)
- [parameterized tests](https://www.baeldung.com/parameterized-tests-junit-5)
- [open csv](https://www.youtube.com/watch?v=1jzGHF8bpn0)
- [spring security - wip](https://www.manning.com/books/spring-security-in-action-second-edition)
- [spring events](https://www.baeldung.com/spring-events)
- [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html)
- [4 common pitfalls BigDecimal](https://blogs.oracle.com/javamagazine/post/four-common-pitfalls-of-the-bigdecimal-class-and-how-to-avoid-them)
- [clean code](https://www.baeldung.com/java-clean-code)
- [spring-email](https://www.baeldung.com/spring-email)
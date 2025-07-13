# lease-a-car-api

## Requirements

- Java 17+
- Maven

## How to run

1.  Clone the project:

              git clone https://github.com/lvanrooijen/lease-a-car-api.git

2.  `mvn install`
3.  `mvn spring-boot:run`

    - The API will be running on http://localhost:8080

## Side note:

The calculateLeaseRate method is
located [here](https://github.com/lvanrooijen/lease-a-car-api/blob/main/src/main/java/com/lvr/lease_a_car/entities/car/CarService.java) (
starting at line 184), and the corresponding tests start
at [line 23](https://github.com/lvanrooijen/lease-a-car-api/blob/main/src/test/java/com/lvr/lease_a_car/entities/car/CarServiceTest.java).

No rounding rules were provided, I attempted to match the results to common calculator (as a result of this some tests
fail).
Based on research i chose HALF_EVEN rounding, which is standard in financial applications.
In a real-world scenario, I would ask for more information.

Swagger Documentation here: http://localhost:8080/swagger-ui/index.html

The `post-submission-improvements` branch contains changes made after the deadline passed. 
These changes are not part of the original submission and are intended as personal improvements only.

## Structure & Design

### _This project contains the following directories:_

#### _`exception` folder containing custom exceptions and a global exception handler_

#### _`security` folder for Spring Security related classes_

#### _`entity` folder which contains:_

- `Entity` : Represents the entity/model (Car, Customer)

- `Controller` : Controller class for handling HTTP requests

- `Service` : A Service class that handles the business logic related to the entity

- `Repository` : Handles querying the database

- `DTO Folder` : Folder containing DTOs I often use:
  - `Get[name of entity]`: What is returned to the controller. Contains a factory method `to()` to convert the entity
    to this DTO.
  - `Post[name of entity]`: Used as a request body when posting a new entity. Occasionally contains a factory method
    `from()` to convert the DTO to an entity.
  - `Patch[name of entity]`: Used as a request body for patching an entity.

#### _`utility` folder containing the following folders:_

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

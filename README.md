# lease-a-car-api

## Requirements
- Java 17+
- Maven

## How to run

1. Clone the project:
      
              git clone https://github.com/lvanrooijen/lease-a-car-api.git
2. `mvn install`
3. `mvn spring-boot:run`
   or
   [![image](https://github.com/user-attachments/assets/c448a1c6-24f2-438a-9763-df3f5765c057)](https://github.com/lvanrooijen/lease-a-car-api/blob/main/src/main/resources/static/didi.png)

      - The API will be running on http://localhost:8080

## Good to know:
Calculate lease-rate method can be found [here](https://github.com/lvanrooijen/lease-a-car-api/blob/main/src/main/java/com/lvr/lease_a_car/entities/car/CarService.java) and starts on line 183

Calculcate lease rate [tests](https://github.com/lvanrooijen/lease-a-car-api/blob/main/src/test/java/com/lvr/lease_a_car/entities/car/CarServiceTest.java) start at line 23, it fails one of the tests and is off by 0.01 and i am not sure why atm. 

Swagger Documentation here: http://localhost:8080/swagger-ui/index.html 

## Structure & Design

### *This project contains the following directories:*

#### *`exception` folder containing custom exceptions and a global exception handler*

#### *`security` folder for Spring Security related classes*

#### *`entity` folder which contains:*

-  `Entity` : Represents the entity/model (Car, Customer)

-  `Controller` : Controller class for handling HTTP requests

-  `Service` : A Service class that handles the business logic related to the entity

-  `Repository` : Handles querying the database

-  `DTO Folder` : Folder containing DTOs I often use:
   * `Get[name of entity]`: What is returned to the controller. Contains a factory method `to()` to convert the entity to this DTO.
   *  `Post[name of entity]`: Used as a request body when posting a new entity. Occasionally contains a factory method `from()` to convert the DTO to an entity.
   *  `Patch[name of entity]`: Used as a request body for patching an entity.

#### *`utility` folder containing the following folders:*
- `annotations` : Contains custom annotations for validation, primarily used for validating values in request bodies through DTOs.
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

2. **Admin**:
   - **Username**: `admin@email.com`
   - **Password**: `SecurePassword123!`
   - **Role**: `ROLE_ADMIN`



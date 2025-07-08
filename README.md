# lease-a-car-api

Simple Lease-a-Car API

## Requirements
- Java 17+
- Maven

## How to run

1. Clone the project:
      
              git clone https://github.com/lvanrooijen/lease-a-car-api.git
2. mvn install
3. mvn spring-boot:run
      - The API will be running on http://localhost:8080
  

## Endpoints

You can access all the API endpoints and documentation via Swagger UI at:

         http://localhost:8080/swagger-ui/index.html

Users:
- `POST /api/v1/users/register` 
- `DELETE /api/v1/users/{id}`      (admin access only)
- `PATCH /api/v1/users/{id}`      

Customers:
- `POST /api/v1/customers`               
- `DELETE /api/v1/customers/{id}`      (admin & broker access only)
- `PATCH /api/v1/customer/{id}`      (admin & broker access only)

Cars:
- `GET /api/v1/cars`      (soft deleted cars show for admin only)
- `POST /api/v1/cars`      (admin access only)
- `GET /api/v1/cars/{id}` (soft deleted items show for admin only)
- `DELETE /api/v1/cars/{id}`      (admin access only)
- `PATCH /api/v1/cars/{id}`      (admin access only)
- `GET /api/v1/cars/lease-rate` requires query paramaters

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

~For safety reasons a very secure password has been carefully selected~

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



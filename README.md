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

You can access all the API endpoints and detailed documentation via Swagger UI at:

         http://localhost:8080/swagger-ui/index.html

Users:
- `POST /api/v1/users/register`
- `DELETE /api/v1/users/{id}`
- `PATCH /api/v1/users/{id}`

Customers:
- `POST /api/v1/customers`
- `DELETE /api/v1/customers/{id}`
- `PATCH /api/v1/customer/{id}` 

Cars:
- `GET /api/v1/cars`
- `POST /api/v1/cars`
- `GET /api/v1/cars/{id}`
- `DELETE /api/v1/cars/{id}`
- `PATCH /api/v1/cars/{id}`
- `GET /api/v1/cars/lease-rate` requires query paramaters 

## Seeded Users
Users for testing have been seeded, you can login with the following credentials:

~For safety reasons a very secure password has been carefully selected~

1. **Broker**:
   - **Username**: `broker@email.com`
   - **Password**: `SecurePassword123!`
   - **Role**: `ROLE_BROKER`
   - permissions: WIP

2. **Employee**:
   - **Username**: `employee@email.com`
   - **Password**: `SecurePassword123!`
   - **Role**: `ROLE_EMPLOYEE`
   - permissions: WIP

2. **Admin**:
   - **Username**: `admin@email.com`
   - **Password**: `SecurePassword123!`
   - **Role**: `ROLE_ADMIN`
   - permissions: WIP
  
## Structure & Design

### I divide my project into separate folders for each entity. Each entity folder contains the following:

1. **Entity**
   - Represents the entity/model (Car, Customer)

2. **Controller**
   - Controller class for handling HTTP requests

3. **Service**
   - A Service class that handles the business logic related to the entity

4. **Repository**
   - Handles querying the database

5. **DTO Folder**
   - Folder containing DTOs I often use:
     - [ ] `Get[name of entity]`: What is returned to the controller. Contains a factory method `to()` to convert the entity to this DTO.
     - [ ] `Post[name of entity]`: Used as a request body when posting a new entity. Occasionally contains a factory method `from()` to convert the DTO to an entity.
     - [ ] `Patch[name of entity]`: Used as a request body for patching an entity.
    
### An exception folder which contains custom exceptions and a global exception handler

### A security folder for Security Configuration (in this case basic authentication is used)

### A utility folder 
- [ ] annotations contains custom annotations for validation. 
- [ ] constants contains classes that hold constant values. (Endpoints, ExceptionMessages)


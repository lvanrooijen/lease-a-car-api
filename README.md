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

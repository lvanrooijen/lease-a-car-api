# lease-a-car-api

Originally created as a take-home assessment, now extended into a personal (overengineered) learning project.
for more information visit the projects [wiki page](https://github.com/lvanrooijen/lease-a-car-api/wiki)

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
- Docker

## How to run

1. Clone the project:

             git clone https://github.com/lvanrooijen/lease-a-car-api.git

2. `docker compose up --build`
3. `mvn install`
4. `mvn spring-boot:run` or 
   [![image](https://github.com/user-attachments/assets/c448a1c6-24f2-438a-9763-df3f5765c057)](https://www.youtube.com/watch?v=MtaTKXJ89jk)

The API runs locally, services run via docker




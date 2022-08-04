# User Manager
Prototype service to manage users, allowing Create, Read, Update, Delete (CRUD) operations and search.

## Technologies
- Java 11
- Spring Boot
- H2 Database: in memory database
- Flyway: use as a database migration tool
- Maven: Build tool developed
- Swagger: Document and test the APIs 
- Actuator: for monitoring the application health.

## How to run the service:
The spring boot service can be run from command line using the maven wrapper mvnw:
```
./mvnw spring-boot:run 

```

### APIs

#### GetUserById GET /users/{userId}
#### createUser POST /users
#### updateUser PUT /users/{userId}
#### deleteUser DELETE /users/{userId}

use delete endpoint with caution, it will perform a removal from the database.
Due to GDPR constrains when the delete API is called no records of the user will be kept in the service.

### Testing
Unit and integration tests for the service are located in the src/test package

### SwaggerUI 
To try the APIs Swagger is available http://localhost:8080/swagger-ui/index.html

## Zuul integration
Due to Spring Cloud Netflix Projects Entering Maintenance Mode, more information here:
https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now#spring-cloud-netflix-projects-entering-maintenance-mode
Zuul has been replaced for Spring Cloud Gateway
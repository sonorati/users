# User Manager
Prototype service to manage users, allowing Create, Read, Update, Delete (CRUD) operations and search.

## Technologies
- Java 11
- Spring Boot
- Docker
- H2 Database: in memory database
- Flyway: use as a database migration tool
- Maven: Build tool developed
- Swagger: Document and test the APIs 
- Actuator: for monitoring the application health.

## How to run the service:
The spring boot service can be run from command line using the maven wrapper mvnw:
```
$> ./mvnw spring-boot:run 

```

## How the user service inside docker container:
To create an image from the Dockerfile, run ‘docker build':

```
$> docker build --tag=user-service:latest . 
```

Finally, run the container from the image:
```
$> docker run -p8888:8080 user-service:latest
 
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

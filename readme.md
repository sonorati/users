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
- Actuator: for monitoring application health.

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
API used to find a user by providing their ID, a 200 response code with user details in the body will be returned.

#### createUser POST /users
API that will allow user creation, the request body has currently one mandatory value which is the user Surname.
When successfully creating a user a 201 (Created) response code is returned with a representation of the resource in response body.  

#### updateUser PUT /users/{userId}
PUT API allows updating user data by sending the changes in the request body.
As per RFC standards (https://www.rfc-editor.org/rfc/rfc7231.html#section-4.3.4)
If the target user does not have a current representation and the
PUT successfully creates one, then the API inform the user agent by sending a 201 (Created). 
If the target user does have a representation and that representation
is successfully modified in then the API sends a 200 (OK).

#### deleteUser DELETE /users/{userId}
Use delete endpoint with caution, it will delete the record from the database.
Due to GDPR, when the delete API is called no records of the user will be kept in the service.

#### searchUsers POST /users/search
Search Api implementation supports search by ID, firstName, Surname. It will return a list of users.
Instead of using parameters in the url with a GET endpoint, the search works using a POST endpoint with the search criteria en the body.
This prevents from exposing PII data in the url.

### Testing
Unit and integration tests for the service are located in the src/test package

### SwaggerUI 
To try the APIs Swagger is available http://localhost:8080/swagger-ui/index.html

package com.seon.user.integration;

import com.seon.user.User;
import com.seon.user.UserApplication;
import com.seon.user.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserApplication.class)
public class UserIntegrationTest {

    private final static String USER_REQUEST_BODY = "{\n" +
            "  \"firstName\": \"Steve\",\n" +
            "  \"surname\": \"Vai\",\n" +
            "  \"jobTitle\": \"Musician\",\n" +
            "  \"dob\": \"08/01/1950\",\n" +
            "  \"id\": \"1\" \n}";

    @LocalServerPort
    private int port;

    @Autowired
    UserRepository repository;

    @PostConstruct
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenUnknownUser_getRequestShouldBe404() {
        UUID userId = UUID.randomUUID();
        String getUrl = "/users/" + userId;

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(getUrl)
                .then()
                .extract().response();

        assertEquals(404, response.statusCode());
        assertEquals("User Not Found", response.jsonPath().getString("message"));
    }

    @Test
    public void whenUsingValidID_getRequestShouldReturn_200() {
        User savedUser = repository.save(makeUser());
        String getUrl = "/users/" + savedUser.getId();

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(getUrl)
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("Seb", response.jsonPath().getString("firstName"));
        assertEquals("Onorati", response.jsonPath().getString("surname"));
    }

    @Test
    public void whenUsingValidID_deleteRequestShouldReturn_204() {
        User savedUser = repository.save(makeUser());
        String deleteUrl = "/users/" + savedUser.getId();

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(deleteUrl)
                .then()
                .extract().response();

        assertEquals(204, response.statusCode());
        assertThat(repository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    public void whenUpdating_putRequestShouldReturn_200() {
        User savedUser = repository.save(makeUser());
        String putUrl = "/users/" + savedUser.getId();

        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(USER_REQUEST_BODY)
                .when()
                .put(putUrl)
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("Steve", response.jsonPath().getString("firstName"));
        assertEquals("Vai", response.jsonPath().getString("surname"));
        assertEquals("Musician", response.jsonPath().getString("jobTitle"));
    }

    @Test
    public void whenUpdatingNewUser_putRequestShouldReturn_201() {
        String putUrl = "/users/" + UUID.randomUUID();

        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(USER_REQUEST_BODY)
                .when()
                .put(putUrl)
                .then()
                .extract().response();

        assertEquals(201, response.statusCode());
        assertEquals("Steve", response.jsonPath().getString("firstName"));
        assertEquals("Vai", response.jsonPath().getString("surname"));
        assertEquals("Musician", response.jsonPath().getString("jobTitle"));
    }

    @Test
    public void whenCreatingUser_postRequestShouldReturn_201() {
        String putUrl = "/users/";

        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(USER_REQUEST_BODY)
                .when()
                .post(putUrl)
                .then()
                .extract().response();

        assertEquals(201, response.statusCode());
        assertEquals("Steve", response.jsonPath().getString("firstName"));
        assertEquals("Vai", response.jsonPath().getString("surname"));
        assertEquals("Musician", response.jsonPath().getString("jobTitle"));
    }

    @Test
    public void whenSearchByName_shouldReturnListOfUsers() {
        String searchByFistName = "{\n \"firstName\": \"seb\" \n}";

        User sebastian = repository.save(makeUser("Sebastian"));
        User seb = repository.save(makeUser("Seb"));
        repository.save(makeUser("Richard"));

        String searchUrl = "/users/search";

        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(searchByFistName)
                .when()
                .post(searchUrl)
                .then()
                .extract().response();

         List<Map<String, String>> users = response.jsonPath().getList("");

        assertEquals(200, response.statusCode());
        assertEquals(2, response.jsonPath().getList("").size());
        assertEquals("Sebastian", users.get(0).get("firstName"));
        assertEquals("Seb", users.get(1).get("firstName"));
        assertEquals(sebastian.getId().toString(), users.get(0).get("id"));
        assertEquals(seb.getId().toString(), users.get(1).get("id"));
    }

    private static User makeUser(String firstName) {
        return User.builder()
                .id(UUID.randomUUID())
                .title("Mr")
                .firstName(firstName)
                .surname("Onorati")
                .dob("14/04/2020")
                .jobTitle("Dev")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    private static User makeUser() {
        return makeUser("Seb");
    }
}

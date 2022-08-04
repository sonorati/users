package com.seon.user.integration;

import com.seon.user.User;
import com.seon.user.UserApplication;
import com.seon.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = UserApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    private static final String USER = "{\"firstName\": \"bob\", \"title\" : \"Mr\"}";

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void shouldGetUserDetails() throws Exception {
        User savedUser = repository.save(makeUser());
        String getUserUrl = "/users/" + savedUser.getId();

        mvc.perform(get(getUserUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Seb")))
                .andExpect(jsonPath("$.surname", is("Onorati")));
    }

    @Test
    public void WhenGettingWithInvalidUserId_shouldReturnNotFound() throws Exception {
        String usersUrl = "/users/" + UUID.randomUUID();

        mvc.perform(get(usersUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User savedUser = repository.save(makeUser());
        String usersUrl = "/users/" + savedUser.getId();

        mvc.perform(delete(usersUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(savedUser.getId())).isEmpty();
    }
    @Test
    public void WhenInvalidUserId_shouldReturnNotFound() throws Exception {
        String usersUrl = "/users/" + UUID.randomUUID();

        mvc.perform(delete(usersUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private static User makeUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .title("Mr")
                .firstName("Seb")
                .surname("Onorati")
                .dob("14/04/2020")
                .jobTitle("Dev")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

}

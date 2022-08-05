package com.seon.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seon.user.dto.UserRequestDto;
import com.seon.user.dto.UserResponseDto;
import com.seon.user.dto.UserSearchRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final UUID USER_ID = UUID.randomUUID();
    public static final String USER_URL = "/users/" + USER_ID;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void shouldGetUser() throws Exception {
        UserResponseDto user = UserResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName("Seb")
                .surname("Onorati")
                .build();

        given(service.getUser(USER_ID)).willReturn(user);

        mvc.perform(get(USER_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Seb")))
                .andExpect(jsonPath("$.surname", is("Onorati")));
    }

    @Test
    public void shouldPUTUser() throws Exception {
        UserRequestDto user = UserRequestDto.builder()
                .firstName("Seb")
                .surname("Onorati")
                .build();
        UserResponseDto userResponse = UserResponseDto.builder()
                .firstName("Seb").surname("Onorati").build();

        given(service.findUser(USER_ID)).willReturn(Optional.of(makeUser()));
        given(service.updateUser(user, makeUser())).willReturn(userResponse);

        mvc.perform(put(USER_URL)
                        .content(asJsonString(UserRequestDto.builder()
                                .firstName("Seb")
                                .surname("Onorati")
                                .jobTitle("Lawyer").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateUsingPUT() throws Exception {
        UserRequestDto user = UserRequestDto.builder()
                .firstName("Seb")
                .surname("Onorati")
                .build();
        UserResponseDto userResponse = UserResponseDto.builder()
                .firstName("Seb").surname("Onorati").build();

        given(service.findUser(USER_ID)).willReturn(Optional.empty());
        given(service.saveUserWithId(USER_ID, user)).willReturn(userResponse);

        mvc.perform(put(USER_URL)
                        .content(asJsonString(UserRequestDto.builder()
                                .firstName("Seb")
                                .surname("Onorati")
                                .jobTitle("Lawyer").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldCreateUsingPost() throws Exception {
        UserRequestDto user = UserRequestDto.builder()
                .firstName("Seb")
                .surname("Onorati")
                .build();
        UserResponseDto userResponse = UserResponseDto.builder()
                .firstName("Seb").surname("Onorati").build();

        given(service.saveUser(user)).willReturn(userResponse);

        mvc.perform(post("/users")
                        .content(asJsonString(UserRequestDto.builder()
                                .firstName("Seb")
                                .surname("Onorati")
                                .jobTitle("Lawyer").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSearchForUsers() throws Exception {
        UserSearchRequestDto user = UserSearchRequestDto.builder()
                .firstName("Seb")
                .surname("Onorati")
                .build();
        UserResponseDto userResponse = UserResponseDto.builder()
                .firstName("Seb").surname("Onorati").build();

        given(service.searchUsers(user)).willReturn(List.of(userResponse));

        mvc.perform(post("/users/search")
                        .content(asJsonString(UserSearchRequestDto.builder()
                                .surname("Onorati")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteUser_shouldReturnNoContent() throws Exception {
        mvc.perform(delete(USER_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
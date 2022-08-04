package com.seon.user;


import com.seon.user.dto.UserResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void shouldDeleteUser_shouldReturnNoContent() throws Exception {
        mvc.perform(delete(USER_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
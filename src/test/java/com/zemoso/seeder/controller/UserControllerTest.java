package com.zemoso.seeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        reset(userService);
    }

    @Test
    void getUser_ShouldReturnUserDetails() throws Exception {
        // Create a mock user
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Rishabh");
        mockUser.setPassword("abc");
        mockUser.setEmail("rishabh.sharma@zemosolabs.com");
        mockUser.setAvailableCredit(100.00);

        when(userService.getById(1L)).thenReturn(mockUser);

        ResultActions response = mockMvc.perform(get("/user/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rishabh"))
                .andExpect(jsonPath("$.email").value("rishabh.sharma@zemosolabs.com"));
    }


    @Test
    void addUser_ShouldReturnUserDetails_WhenNewUserAdded() throws Exception {
        UserDto mockUser = new UserDto();
        mockUser.setName("Rishabh");
        mockUser.setPassword("abc");
        mockUser.setEmail("rishabh.sharma@zemosolabs.com");
        mockUser.setAvailableCredit(100.00);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Rishabh");
        newUser.setPassword("abc");
        newUser.setEmail("rishabh.sharma@zemosolabs.com");
        newUser.setAvailableCredit(100.00);

        when(userService.createUser(any(UserDto.class))).thenReturn(newUser);

        ResultActions response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUser)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(newUser.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(newUser.getEmail())));
    }

    @Test
    void updateUser_ShouldReturnUserDetails_WhenOldUserDetailsAreUpdated() throws Exception {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Rishabh Sharma");
        newUser.setPassword("abc");
        newUser.setEmail("rishabh.sharma@zemosolabs.com");
        newUser.setAvailableCredit(100000.00);
        String userJson = objectMapper.writeValueAsString(newUser);

        when(userService.updateUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON) // Content type is JSON
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rishabh Sharma"))
                .andExpect(jsonPath("$.availableCredit").value(100000.00));
    }
}

package com.zemoso.seeder.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.seeder.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHandleGeneralException() throws Exception {
        Exception exception = new Exception("Internal Server Error");

        UserDto mockUser = new UserDto();
        mockUser.setName("Rishabh");
        mockUser.setPassword("abc");
        mockUser.setEmail(null);
        mockUser.setAvailableCredit(100.00);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser))
                        .requestAttr("exception", exception)) // Mock exception
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }

    @Test
    void testHandleResourceNotFoundException() throws Exception {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        mockMvc.perform(get("/user/5")
                        .requestAttr("exception", exception)) // Mock exception
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with ID: 5"));
    }
}

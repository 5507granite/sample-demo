package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllUsers_ReturnsOk() throws Exception {
        User user = new User(); user.setName("Miki"); user.setEmail("m@m.com");
        userRepository.save(user);

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Miki"));
    }

    @Test
    void createUser_ReturnsCreatedUser() throws Exception {
        UserDTO dto = new UserDTO(null, "Test", "test@u.com");
        mockMvc.perform(post("/api/users")
            .content(objectMapper.writeValueAsString(dto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void getUserById_ReturnsUser() throws Exception {
        User user = new User(); user.setName("Mini"); user.setEmail("mini@example.com");
        user = userRepository.save(user);

        mockMvc.perform(get("/api/users/" + user.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Mini"));
    }
}
package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers_ReturnsList() {
        User u1 = new User(); u1.setId(1L); u1.setName("Alice"); u1.setEmail("a@example.com");
        User u2 = new User(); u2.setId(2L); u2.setName("Bob"); u2.setEmail("b@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getName());
    }

    @Test
    void getUserById_Found() {
        User user = new User(); user.setId(1L); user.setName("Test"); user.setEmail("t@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertEquals("Test", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserById_NotFound_ThrowsException() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(2L));
    }

    @Test
    void createUser_SavesUser() {
        UserDTO dto = new UserDTO(null, "Create", "c@c.com");
        User saved = new User(); saved.setId(10L); saved.setName("Create"); saved.setEmail("c@c.com");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserDTO result = userService.createUser(dto);

        assertEquals("Create", result.getName());
        assertNotNull(result.getId());
    }
}
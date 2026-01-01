package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    private static UserDTO toDTO(User u) {
        return new UserDTO(u.getId(), u.getName(), u.getEmail());
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
            .stream().map(UserService::toDTO).collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User u = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return toDTO(u);
    }
}
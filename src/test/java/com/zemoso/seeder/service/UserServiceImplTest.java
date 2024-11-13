package com.zemoso.seeder.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.exception.ResourceNotFoundException;
import com.zemoso.seeder.repository.UserRepository;
import com.zemoso.seeder.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "Rishabh Sharma", "rishabh@example.com", "password123", 1000d);
        userDto = new UserDto("Rishabh Sharma", "rishabh@example.com", "password123", 1000d);
    }

    @Test
    void testGetById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    void testGetById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.getById(1L));
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testCreateUser() {
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User(1L, "Rishabh Sharma", "rishabh@example.com", "password123", 1000d);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getAvailableCredit(), result.getAvailableCredit());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User updatedUser = new User(1L, "Rishabh Sharma", "rishabh@example.com", "password123", 1000d);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(updatedUser));
        assertEquals("User not found with ID: 1", exception.getMessage());
    }
}


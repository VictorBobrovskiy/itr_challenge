package com.itr.challenge.service;
import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.error.UserExistsException;
import com.itr.challenge.error.UserNotFoundException;
import com.itr.challenge.mapper.UserMapper;
import com.itr.challenge.model.Phone;
import com.itr.challenge.model.User;
import com.itr.challenge.repository.PhoneRepository;
import com.itr.challenge.repository.UserRepository;
import com.itr.challenge.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken("token");
        user.setActive(true);

        userRequestDto = new UserRequestDto("John Doe", "john.doe@example.com", "password", Collections.emptySet());

    }

    @Test
    void testRegister() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken(anyString())).thenReturn("token");

        UserDto createdUser = userService.register(userRequestDto);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertNotNull(createdUser.getToken());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(phoneRepository, times(1)).saveAll(anySet());
    }

    @Test
    void testRegisterDuplicateEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserExistsException.class, () -> {
            userService.register(userRequestDto);
        });

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(0)).save(any(User.class));
        verify(phoneRepository, times(0)).saveAll(anySet());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDto foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDto> userList = userService.getAllUsers();

        assertEquals(1, userList.size());
        assertEquals(user.getId(), userList.get(0).getId());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedUser = userService.updateUser(1L, userRequestDto);

        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());


        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, userRequestDto);
        });

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any(User.class));
    }
}

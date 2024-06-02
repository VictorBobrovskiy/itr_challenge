package com.itr.challenge.controller;

import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.error.UserNotFoundException;
import com.itr.challenge.mapper.UserMapper;
import com.itr.challenge.model.User;
import com.itr.challenge.security.JwtUtil;
import com.itr.challenge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    private UserDto userDto;

    private User user;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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

        userDto = UserMapper.toUserDto(user);

        userRequestDto = new UserRequestDto("John Doe", "john.doe@example.com", "password", Collections.emptySet());

    }

    @Test
    void testRegister() throws Exception {
        when(userService.register(any(UserRequestDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password1\", \"phones\": [] }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"));

        verify(userService, times(1)).register(any(UserRequestDto.class));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new UserNotFoundException("User with id 1 not found"));

        mockMvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"mensaje\": \"User with id 1 not found\"}"));

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDto));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].token").value("token"));

        verify(userService, times(1)).getAllUsers();
    }


    @Test
    void testUpdateUserNotFound() throws Exception {
        when(userService.updateUser(anyLong(), any(UserRequestDto.class))).thenThrow(new UserNotFoundException("User with id 2 not found"));

        mockMvc.perform(put("/users/{id}", 2L, userRequestDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John Doe Updated\", \"email\": \"john.doe.updated@example.com\", \"password\": \"newpassword\", \"phones\": [] }"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"mensaje\": \"User with id 2 not found\"}"));

        verify(userService, times(1)).updateUser(anyLong(), any(UserRequestDto.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(anyLong());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        doThrow(new UserNotFoundException("User with id 1 not found")).when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"mensaje\": \"User with id 1 not found\"}"));

        verify(userService, times(1)).deleteUser(anyLong());
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(1L, userRequestDto)).thenReturn(userDto);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John Doe Updated\", \"email\": \"john.doe.updated@example.com\", \"password\": \"newpassword\", \"phones\": [] }"))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(anyLong(), any(UserRequestDto.class));
    }
}
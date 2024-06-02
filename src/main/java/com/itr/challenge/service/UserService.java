package com.itr.challenge.service;

import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto getUserById(long id);

    UserDto register(UserRequestDto userRequestDto);

    List<UserDto> getAllUsers();

    void deleteUser(long id);

    UserDto updateUser(long ig, UserRequestDto newUser);
}

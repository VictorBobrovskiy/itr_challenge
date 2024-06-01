package com.itr.challenge.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.error.UserNotFoundException;
import com.itr.challenge.mapper.UserMapper;
import com.itr.challenge.model.User;
import com.itr.challenge.repository.PhoneRepository;
import com.itr.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto register(UserRequestDto userRequestDto) {

        User user = UserMapper.toUser(userRequestDto);

        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);

        UserDto userDto = UserMapper.toUserDto(userRepository.save(user));

        log.debug("----- User with id {} registered", userDto.getId());

        return userDto;
    }

    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + "not found"));
        log.debug("----- User with id {} found", userId);
        return UserMapper.toUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public UserDto updateUser(long userId, User newUser) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + "not found"));

        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setPhones(newUser.getPhones());
        user.setModified(LocalDateTime.now());
        log.debug("----- User with id {} updated", userId);
        return UserMapper.toUserDto(user);
    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + "not found"));
        user.setActive(false);
        userRepository.save(user);
        log.debug("----- User with id {} deleted", userId);
    }
}

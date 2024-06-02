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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PhoneRepository phoneRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final JwtUtil jwtUtil;


    @Transactional
    public UserDto register(UserRequestDto userRequestDto) {

        Optional<User> existingUser = userRepository.findByEmail(userRequestDto.getEmail());

        if (existingUser.isPresent()) {
            throw new UserExistsException("El correo ya registrado");
        }

        User user = UserMapper.toUser(userRequestDto);

        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);

        String token = jwtUtil.generateToken(userRequestDto.getEmail());
        user.setToken(token);

        User savedUser = userRepository.save(user);

        Set<Phone> userPhones = user.getPhones();

        userPhones.forEach(p -> p.setUser(savedUser));

        phoneRepository.saveAll(userPhones);

        log.debug("----- User with id {} registered", savedUser.getId());

        return UserMapper.toUserDto(savedUser);
    }

    public UserDto getUserById(long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + "not found"));

        log.debug("----- User with id {} found", userId);

        return UserMapper.toUserDto(user);
    }

    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll(Pageable.ofSize(10)).toList();

        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(long userId, UserRequestDto newUser) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + "not found"));

        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setModified(LocalDateTime.now());

        Set<Phone> userPhones = newUser.getPhones().stream().map(UserMapper::toPhone).collect(Collectors.toSet());
        user.setPhones(userPhones);

        log.debug("----- User with id {} updated", userId);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    public void deleteUser(long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + "not found"));

        user.setActive(false);

        userRepository.save(user);

        log.debug("----- User with id {} deleted", userId);
    }
}

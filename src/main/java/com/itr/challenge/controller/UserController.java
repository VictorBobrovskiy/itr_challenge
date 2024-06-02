package com.itr.challenge.controller;

import com.itr.challenge.dto.UserDto;
import com.itr.challenge.dto.UserRequestDto;
import com.itr.challenge.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {

        UserDto createdUser = userService.register(userRequestDto);

        log.debug("----- New user request for user with email {} received", userRequestDto.getEmail());

        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {

        log.debug("----- Request for user with id {} received", id);

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        log.debug("----- Request for all users received");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody UserRequestDto userDetails) {

        log.debug("----- Update request for user with id {} received", id);

        return ResponseEntity.ok(userService.updateUser(id, userDetails));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {

        userService.deleteUser(id);

        log.debug("----- Delete request for user with id {} received", id);

        return ResponseEntity.noContent().build();
    }
}
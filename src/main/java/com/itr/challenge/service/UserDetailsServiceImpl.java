package com.itr.challenge.service;

import com.itr.challenge.error.UserNotFoundException;
import com.itr.challenge.model.User;
import com.itr.challenge.repository.UserRepository;
import com.itr.challenge.security.JwtFilter;
import com.itr.challenge.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User with email " + username + " not found"));
        log.debug("----- User with email {} found", username);
        return new UserDetailsImpl(user);
    }
}

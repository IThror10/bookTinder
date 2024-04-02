package com.binder.service;

import com.binder.entity.User;
import com.binder.exception.NotFoundException;
import com.binder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User getByUserId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}

package com.binder.service;

import com.binder.entity.User;
import com.binder.exception.ConflictException;
import com.binder.exception.NotFoundException;
import com.binder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername()))
            throw new ConflictException("Username is already used. Try another one");
        else if (repository.existsByEmail(user.getEmail()))
            throw new ConflictException("Email is already used. Isn't it you?");
        else if (repository.existsByPhone(user.getPhone()))
            throw new ConflictException("Phone is already used. Isn't it you?");
        return repository.save(user);
    }

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

package com.binder.service;

import com.binder.entity.User;
import com.binder.exception.ConflictException;
import com.binder.exception.NotFoundException;
import com.binder.repository.UserRepository;
import com.binder.request.ChangeUserDataRequest;
import com.binder.response.UserInfoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    @Transactional
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername()))
            throw new ConflictException("Login is already used. Try another one");
        return repository.save(user);
    }

    @Transactional
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public UserInfoResponse getByUserId(Long id) {
        return new UserInfoResponse(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Transactional
    public UserInfoResponse updateUserInfo(Long userId, ChangeUserDataRequest request) {
        User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("Unexpected error"));
        user.setName(request.name());
        user.setContacts(request.contacts());
        user.setYear(request.year());
        if (request.photo() != null)
            user.setPhoto(request.photo());
        return new UserInfoResponse(repository.save(user));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}

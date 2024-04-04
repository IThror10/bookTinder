package com.binder.service;

import com.binder.entity.User;
import com.binder.exception.NotFoundException;
import com.binder.request.AuthorizeUserRequest;
import com.binder.request.RegisterUserRequest;
import com.binder.response.UserInfoResponse;
import com.binder.response.UserTokenResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserTokenResponse signUp(RegisterUserRequest request) {

        User user = User.builder()
                .username(request.login())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .contacts(request.contacts())
                .year(request.year())
                .build();

        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return new UserTokenResponse(new UserInfoResponse(user), jwt);
    }

    @Transactional
    public UserTokenResponse signIn(AuthorizeUserRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));

            User user = userService.getByUsername(request.getUsername());
            String jwt = jwtService.generateToken(user);
            return new UserTokenResponse(new UserInfoResponse(user), jwt);
        } catch (BadCredentialsException e) {
            throw new NotFoundException("Wrong Email/Password");
        }
    }
}
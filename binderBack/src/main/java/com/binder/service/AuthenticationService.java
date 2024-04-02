package com.binder.service;

import com.binder.entity.User;
import com.binder.request.AuthorizeUserRequest;
import com.binder.request.RegisterUserRequest;
import com.binder.response.UserInfoResponse;
import com.binder.response.UserTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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

    public UserTokenResponse signUp(RegisterUserRequest request) {

        User user = User.builder()
                .username(request.login())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return new UserTokenResponse(new UserInfoResponse(user), jwt);
    }

    public UserTokenResponse signIn(AuthorizeUserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = userService.getByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);
        return new UserTokenResponse(new UserInfoResponse(user), jwt);
    }
}
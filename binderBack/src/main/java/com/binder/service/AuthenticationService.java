package com.RPA.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

//    public UserTokenResponse signUp(RegisterUserRequest request) {
//
//        User user = User.builder()
//                .username(request.login())
//                .email(request.email())
//                .password(passwordEncoder.encode(request.password()))
//                .phone(request.phone())
//                .role(UserRole.ROLE_USER)
//                .build();
//
//        userService.create(user);
//        String jwt = jwtService.generateToken(user);
//        return new UserTokenResponse(new UserInfoResponse(user), jwt);
//    }
//
//    public UserTokenResponse signIn(AuthorizeUserRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getUsername(),
//                request.getPassword()
//        ));
//
//        User user = userService.getByUsername(request.getUsername());
//        String jwt = jwtService.generateToken(user);
//        return new UserTokenResponse(new UserInfoResponse(user), jwt);
//    }
}
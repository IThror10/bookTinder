package com.binder.controller;

import com.binder.service.AuthenticationService;
import com.binder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final AuthenticationService authService;
    private final UserService userService;

}

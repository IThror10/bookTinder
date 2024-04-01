package com.RPA.controller;

import com.RPA.service.AuthenticationService;
import com.RPA.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final AuthenticationService authService;
    private final UserService userService;

}

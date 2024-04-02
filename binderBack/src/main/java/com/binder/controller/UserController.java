package com.binder.controller;

import com.binder.request.RegisterUserRequest;
import com.binder.request.AuthorizeUserRequest;
import com.binder.response.UserTokenResponse;
import com.binder.service.AuthenticationService;
import com.binder.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final AuthenticationService authService;
    private final UserService userService;

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully create",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTokenResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content),
            @ApiResponse(
                    responseCode = "409",
                    description = "Data already used",
                    content = @Content)
    })
    @PostMapping(value = "", produces = {"application/json"})
    public ResponseEntity<UserTokenResponse> registerUser(
            @RequestBody RegisterUserRequest request
    ) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @Operation(summary = "Authorize user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create JWT token",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTokenResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content)
    })
    @PostMapping(value = "/login", produces = {"application/json"})
    public ResponseEntity<UserTokenResponse> authorizeUser(
            @RequestBody AuthorizeUserRequest request
    ) {
        return ResponseEntity.ok(authService.signIn(request));
    }

}

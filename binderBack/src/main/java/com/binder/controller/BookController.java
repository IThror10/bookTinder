package com.binder.controller;

import com.binder.response.BookResponse;
import com.binder.response.GiveAwayResponse;
import com.binder.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
public class BookController {
    private final BookService service;

    @Operation(summary = "Search books by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "advertisement successfully created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class))
                    }),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity searchBooks(@PathVariable String name) {
        return ResponseEntity.ok(service.searchBooks(name));
    }
}

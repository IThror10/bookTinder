package com.binder.controller;

import com.binder.request.GiveAwayRequest;
import com.binder.response.GiveAwayResponse;
import com.binder.service.GiveAwayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/book")
public class GiveAwayController {
    private final GiveAwayService service;

    @Operation(summary = "Create giveaway advertisement")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "advertisement successfully created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GiveAwayResponse.class))
                    }),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "", produces = {"application/json"})
    public ResponseEntity<GiveAwayResponse> createGiveAway(
            @RequestBody GiveAwayRequest request,
            @RequestAttribute("uid") Long userId
    ) {
        return ResponseEntity.ok(service.addGiveAway(userId, request));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity getAllGiveAwayByUserId (
            @RequestAttribute("uid") Long userId
    ) {
        return ResponseEntity.ok(service.getGiveAwayByUser(userId));
    }

}

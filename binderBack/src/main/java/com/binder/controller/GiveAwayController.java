package com.binder.controller;

import com.binder.request.GiveAwayRequest;
import com.binder.response.GiveAwayResponse;
import com.binder.response.MatchResponse;
import com.binder.service.GiveAwayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

    @Operation(summary = "Returns users' advertisements")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users' giveaway advertisements",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GiveAwayResponse.class)))
                    }),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity getAllGiveAwayByUserId (
            @RequestAttribute("uid") Long userId
    ) {
        return ResponseEntity.ok(service.getGiveAwayByUser(userId));
    }


    @Operation(summary = "Returns others' advertisements")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Advertisements for authorized user",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GiveAwayResponse.class)))
                    }),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/recommend", produces = {"application/json"})
    public ResponseEntity getSwipes (
            @RequestAttribute("uid") Long userId
    ) {
        return ResponseEntity.ok(service.getAvailableAdvertisements(userId));
    }



    @Operation(summary = "Swipe left or right giveaway advertisement")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Swipe left or right was successful",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Giveaway Not Found",
                    content = @Content)
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/recommend/{giveAwayId}", produces = {"application/json"})
    public ResponseEntity swipeGiveAway (
            @RequestAttribute("uid") Long userId,
            @PathVariable Long giveAwayId,
            @RequestBody Boolean like
    ) {
        service.swipeGiveAway(userId, giveAwayId, like);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Returns users' matches")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Advertisements for authorized user",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MatchResponse.class)))
                    }),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/recommend/match", produces = {"application/json"})
    public ResponseEntity getMatches (
            @RequestAttribute("uid") Long userId
    ) {
        return ResponseEntity.ok(service.getMatches(userId));
    }

    @Operation(summary = "Rate book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Book was rated successfully",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book Not Found",
                    content = @Content)
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/{bookId}", produces = {"application/json"})
    public ResponseEntity rateBook (
            @RequestAttribute("uid") Long userId,
            @PathVariable Long bookId,
            @RequestBody Boolean like
    ) {
        service.rateBook(userId, bookId, like);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete giveaway")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Giveaway was deleted successfully",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "GiveAway Not Found",
                    content = @Content),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content)
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/{giveAwayId}", produces = {"application/json"})
    public ResponseEntity rateBook (
            @RequestAttribute("uid") Long userId,
            @PathVariable Long giveAwayId
    ) {
        service.deleteGiveAway(userId, giveAwayId);
        return ResponseEntity.noContent().build();
    }
}
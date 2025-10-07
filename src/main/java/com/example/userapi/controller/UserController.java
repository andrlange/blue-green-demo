package com.example.userapi.controller;

import com.example.userapi.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller for managing User resources.
 *
 * This controller provides endpoints for retrieving user information.
 * It uses an in-memory list for demonstration purposes.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing user resources")
public class UserController {

    private final List<User> users;

    /**
     * Constructor initializes the user list with dummy data.
     */
    public UserController() {
        this.users = new ArrayList<>();
        // Initialize with three dummy users
        users.add(new User(1L, "Alice Johnson", "alice.johnson@example.com"));
        users.add(new User(2L, "Bob Smith", "bob.smith@example.com"));
        users.add(new User(3L, "Charlie Brown", "charlie.brown@example.com"));
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of all users
     */
    @Operation(
        summary = "Get all users",
        description = "Retrieves a complete list of all users in the system. " +
                     "Returns an empty list if no users are available."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list of users",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id the unique identifier of the user
     * @return ResponseEntity containing the user if found, or 404 if not found
     */
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their unique identifier. " +
                     "Returns 404 Not Found if the user does not exist."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the user",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found with the provided ID",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
        @Parameter(
            description = "Unique identifier of the user to retrieve",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    ) {
        return users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

package com.example.userapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * User entity representing a user in the system.
 *
 * This class contains basic user information including id, name, and email.
 */
@Schema(description = "User entity representing a user in the system")
public class User {

    @Schema(
        description = "Unique identifier of the user",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Name is required and cannot be blank")
    @Schema(
        description = "Full name of the user",
        example = "John Doe",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @NotBlank(message = "Email is required and cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Schema(
        description = "Email address of the user",
        example = "john.doe@example.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id    the user's unique identifier
     * @param name  the user's full name
     * @param email the user's email address
     */
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Constructor without id (for creating new users).
     *
     * @param name  the user's full name
     * @param email the user's email address
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // equals, hashCode, and toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(name, user.name) &&
               Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}

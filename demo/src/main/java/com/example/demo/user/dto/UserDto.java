package com.example.demo.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Long id,

        @NotEmpty(message = "Username is required") String username,

        @NotEmpty(message = "Password are required") String password,

        String role) {
}

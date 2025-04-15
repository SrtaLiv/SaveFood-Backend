package com.oliviatodesco.save_food.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO (@NotBlank String email, @NotBlank String password) {
}


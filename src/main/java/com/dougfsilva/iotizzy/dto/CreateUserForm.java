package com.dougfsilva.iotizzy.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserForm(

        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password

) {
}

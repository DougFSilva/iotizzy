package com.dougfsilva.iotizzy.dto;

import com.dougfsilva.iotizzy.model.ProfileType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserForm(

        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotNull ProfileType profileType

) {
}

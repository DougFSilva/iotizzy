package com.dougfsilva.iotnizer.dto;

import com.dougfsilva.iotnizer.model.ProfileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserForm(

        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotNull ProfileType profileType

) {
}

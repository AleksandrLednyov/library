package ru.lednyov.lib.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

    @NotNull
    @NotBlank
    private String surname;

    public AuthorDto(String surname) {
        this.surname = surname;
    }
}

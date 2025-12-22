package ru.lednyov.lib.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AuthorDto {

    private UUID id;
    @NotNull
    @NotBlank
    private String surname;

    public AuthorDto(String surname) {
        this.surname = surname;
    }
}

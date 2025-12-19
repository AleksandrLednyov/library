package ru.lednyov.lib.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.lednyov.lib.domain.Author;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
public class BookDto {

    private UUID id;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    private Set<Author> authors;

    public BookDto(String title, Set<Author> authors) {
        this.title = title;
        this.authors = authors;
    }
}

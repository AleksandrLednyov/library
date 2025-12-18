package ru.lednyov.lib.mapper;

import org.mapstruct.Mapper;
import ru.lednyov.lib.controller.dto.AuthorDto;
import ru.lednyov.lib.controller.dto.BookDto;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.domain.Book;

@Mapper(componentModel = "spring")
public interface AuthorDtoMapper {
    AuthorDto authorToDto(Author author);
    Author DtoToAuthor(AuthorDto dto);
}

package ru.lednyov.lib.mapper;

import org.mapstruct.Mapper;
import ru.lednyov.lib.controller.dto.BookDto;
import ru.lednyov.lib.domain.Book;

@Mapper(componentModel = "spring")
public interface BookDtoMapper {

    BookDto bookToDto(Book book);
    Book dtoToBook(BookDto dto);

    Iterable<BookDto> allBooksToDto(Iterable<Book> books);
}

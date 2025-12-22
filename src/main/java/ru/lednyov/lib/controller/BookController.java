package ru.lednyov.lib.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lednyov.lib.controller.dto.BookDto;
import ru.lednyov.lib.domain.Book;
import ru.lednyov.lib.exception.NotValidRequestException;
import ru.lednyov.lib.mapper.BookDtoMapper;
import ru.lednyov.lib.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookDtoMapper mapper;

    Logger log = LoggerFactory.getLogger(BookController.class);

    @PostMapping
    public ResponseEntity<BookDto> saveNewBook(@Valid @RequestBody BookDto dto) {
        log.info("saveNewBook request received. {}", dto);
        Book savedBook = service.saveNewBook(mapper.dtoToBook(dto));
        dto = mapper.bookToDto(savedBook);
        log.info("New {} saved", dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<Iterable<BookDto>> getAllBooks() {
        log.info("getAllBooks request received");
        Iterable<Book> books = service.getAllBooks();
        log.info("getAllBooks request completed. Result: " + books);
        return ResponseEntity.ok(mapper.allBooksToDto(books));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Iterable<BookDto>> findBooksByTitle(@PathVariable String title) {
        log.info("findBooksByTitle request received");
            Iterable<Book> books = service.findBooksByTitle(title);
            return ResponseEntity.ok(mapper.allBooksToDto(books));
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<Iterable<BookDto>> findBooksByAuthorSurname(@PathVariable String surname) {
        log.info("findBooksByAuthorSurname request received. Author surname is: {}", surname);
            Iterable<Book> books = service.findBooksByAuthorSurname(surname);
            return ResponseEntity.ok(mapper.allBooksToDto(books));
    }

    @PostMapping("/st")
    public ResponseEntity<Iterable<BookDto>> findBooksByAuthorSurnameAndTitle(@RequestParam String surname, @RequestParam String title) {
        log.info("findBooksByAuthorSurnameAndTitle request received. Author surname is: " + surname + " book title: " + title);
            Iterable<Book> books = service.findBooksByAuthorSurnameAndTitle(surname, title);
            return ResponseEntity.ok(mapper.allBooksToDto(books));
    }

    @PatchMapping
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookDto dto) {
        log.info("updateBook request received. {}", dto);
        if (dto.getId() == null) {
            throw new NotValidRequestException("Id is required. " + dto);
        }
        Book updatedBook = service.updateBook(mapper.dtoToBook(dto));
        dto = mapper.bookToDto(updatedBook);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        log.info("deleteBook request received. Book id: {}", id);
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
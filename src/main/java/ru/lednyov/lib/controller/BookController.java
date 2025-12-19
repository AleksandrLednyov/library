package ru.lednyov.lib.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lednyov.lib.controller.dto.BookDto;
import ru.lednyov.lib.domain.Book;
import ru.lednyov.lib.mapper.BookDtoMapper;
import ru.lednyov.lib.service.BookService;
import ru.lednyov.lib.validation.BookValidationError;
import ru.lednyov.lib.validation.BookValidationErrorBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookDtoMapper mapper;

    Logger log = LoggerFactory.getLogger(BookController.class);

    @PostMapping
    public ResponseEntity<?> saveNewBook(@Valid @RequestBody BookDto dto, Errors errors) {
        log.info("saveNewBook request received. {}", dto);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(BookValidationErrorBuilder.fromBindingErrors(errors));
        }
        Book savedBook = service.saveNewBook(mapper.dtoToBook(dto));
        log.info("New {} saved", mapper.bookToDto(savedBook));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(location).build();
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
        try {
            Iterable<Book> books = service.findBooksByTitle(title);
            return ResponseEntity.ok(mapper.allBooksToDto(books));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<Iterable<BookDto>> findBooksByAuthorSurname(@PathVariable String surname) {
        log.info("findBooksByAuthor request received. Author surname is: " + surname);
        try {
            Iterable<Book> books = service.findBooksByAuthorSurname(surname);
            return ResponseEntity.ok(mapper.allBooksToDto(books));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/st")
    public ResponseEntity<Iterable<BookDto>> findBooksByAuthorSurnameAndTitle(@RequestParam String surname, @RequestParam String title) {
        log.info("findBooksByAuthor request received. Author surname is: " + surname + " book title: " + title);
        try {
            Iterable<Book> books = service.findBooksByAuthorSurnameAndTitle(surname, title);
            return ResponseEntity.ok(mapper.allBooksToDto(books));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookDto dto) {
        log.info("updateBook request received. {}", dto);
        if (dto.getId() == null) {
            throw new RuntimeException("Id is required" + dto);
        }
        Book updatedBook = service.updateBook(mapper.dtoToBook(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatedBook.getId())
                .toUri();
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        log.info("deleteBook request received. Book id: {}", id);
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BookValidationError handleException(Exception exception) {
        return new BookValidationError(exception.getMessage());
    }
}
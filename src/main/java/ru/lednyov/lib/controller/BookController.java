package ru.lednyov.lib.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookDtoMapper mapper;

    Logger log = LoggerFactory.getLogger(BookController.class);

    @PostMapping
    public ResponseEntity<?> saveNewBook(@Valid @RequestBody BookDto dto, Errors errors) {
        log.info("SaveNewBook request received. {}", dto);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(BookValidationErrorBuilder.fromBindingErrors(errors));
        }

        Book savedBook = service.saveNewBook(mapper.DtoToBook(dto));
        log.info("New {} saved", savedBook);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BookValidationError handleException(Exception exception) {
        return new BookValidationError(exception.getMessage());
    }
}

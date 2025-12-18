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
import ru.lednyov.lib.controller.dto.AuthorDto;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.mapper.AuthorDtoMapperImpl;
import ru.lednyov.lib.service.AuthorService;
import ru.lednyov.lib.validation.AuthorValidationError;
import ru.lednyov.lib.validation.AuthorValidationErrorBuilder;

import java.net.URI;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;
    private final AuthorDtoMapperImpl mapper;

    Logger log = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping
    public ResponseEntity<?> saveNewAuthor(@Valid @RequestBody AuthorDto dto, Errors errors) {
        log.info("SaveNewAuthor request received. AuthorDto surname: {}", dto.getSurname());
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(AuthorValidationErrorBuilder.fromBindingErrors(errors));
        }
        Author savedAuthor = service.saveNewAuthor(mapper.DtoToAuthor(dto));
        log.info("New {} saved", savedAuthor.toString());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAuthor.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public AuthorValidationError handleException(Exception exception) {
        return new AuthorValidationError(exception.getMessage());
    }
}


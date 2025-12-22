package ru.lednyov.lib.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lednyov.lib.controller.dto.AuthorDto;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.mapper.AuthorDtoMapperImpl;
import ru.lednyov.lib.service.AuthorService;

import java.net.URI;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;
    private final AuthorDtoMapperImpl mapper;

    Logger log = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping
    public ResponseEntity<AuthorDto> saveNewAuthor(@Valid @RequestBody AuthorDto dto) {
        log.info("SaveNewAuthor request received. AuthorDto surname: {}", dto.getSurname());
        Author savedAuthor = service.saveNewAuthor(mapper.DtoToAuthor(dto));
        dto = mapper.authorToDto(savedAuthor);
        log.info("New {} saved", dto.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }
}


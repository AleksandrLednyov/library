package ru.lednyov.lib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author saveNewAuthor(Author author) {
        Author savedBefore = authorRepository.findAuthorBySurname(author.getSurname());
        if (savedBefore == null) {
            return authorRepository.save(author);
        } else throw new RuntimeException("The author " + author.getSurname() + " is already exists");
    }
}

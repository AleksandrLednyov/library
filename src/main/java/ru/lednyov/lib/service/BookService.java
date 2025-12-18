package ru.lednyov.lib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.domain.Book;
import ru.lednyov.lib.repository.AuthorRepository;
import ru.lednyov.lib.repository.BookRepository;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book saveNewBook(Book book) {
        Set<Author> authors = book.getAuthors();
        for (Author author: authors) {
            if (author.getSurname().isEmpty()) throw new RuntimeException("Surname of author for book title " + book.getTitle() + " is empty");
            Author savedBefore = authorRepository.findAuthorBySurname(author.getSurname());
            if (savedBefore != null) {
                author.setId(savedBefore.getId());
            } else {
                Author saved = authorRepository.save(author);
                author.setId(saved.getId());
            }
        }
        return bookRepository.save(book);
    }
}

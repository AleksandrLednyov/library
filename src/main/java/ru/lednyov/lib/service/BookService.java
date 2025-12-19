package ru.lednyov.lib.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.domain.Book;
import ru.lednyov.lib.repository.AuthorRepository;
import ru.lednyov.lib.repository.BookRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {
    Logger log = LoggerFactory.getLogger(BookService.class);

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

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Book> findBooksByTitle(String title) {
        Iterable<Book> books = bookRepository.findBooksByTitle(title);
        if (books.iterator().hasNext()) {
            return books;
        } else {
            log.info("Books by title not found");
            throw new RuntimeException("Books not found");
        }
    }

    public Iterable<Book> findBooksByAuthorSurname(String surname) {
        Iterable<Book> books = bookRepository.findBooksByAuthorSurname(surname);
        if (books.iterator().hasNext()) {
            return books;
        } else {
            log.info("Books by author with surname {} not found", surname);
            throw new RuntimeException("Books not found");
        }
    }

    public Iterable<Book> findBooksByAuthorSurnameAndTitle(String surname, String title) {
        Iterable<Book> books = bookRepository.findBooksByAuthorSurnameAndTitle(surname, title);
        if (books.iterator().hasNext()) {
            return books;
        } else {
            log.info("Books by author with surname {} and title {} not found", surname, title);
            throw new RuntimeException("Books not found");
        }
    }
}

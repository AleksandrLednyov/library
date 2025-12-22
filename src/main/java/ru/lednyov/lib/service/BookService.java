package ru.lednyov.lib.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.lednyov.lib.domain.Author;
import ru.lednyov.lib.domain.Book;
import ru.lednyov.lib.exception.NotValidRequestException;
import ru.lednyov.lib.exception.EntryNotFoundException;
import ru.lednyov.lib.repository.AuthorRepository;
import ru.lednyov.lib.repository.BookRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book saveNewBook(Book book) {
        CheckAndSaveAuthor(book);
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
            log.warn("Books by title {} not found", title);
            throw new EntryNotFoundException("Book by title "  + title + " not found");
        }
    }

    public Iterable<Book> findBooksByAuthorSurname(String surname) {
        Iterable<Book> books = bookRepository.findBooksByAuthorSurname(surname);
        if (books.iterator().hasNext()) {
            return books;
        } else {
            log.warn("Books by author with surname {} not found", surname);
            throw new EntryNotFoundException("Book by author surname "  + surname + " not found");
        }
    }

    public Iterable<Book> findBooksByAuthorSurnameAndTitle(String surname, String title) {
        Iterable<Book> books = bookRepository.findBooksByAuthorSurnameAndTitle(surname, title);
        if (books.iterator().hasNext()) {
            return books;
        } else {
            log.warn("Books by author with surname {} and title {} not found", surname, title);
            throw new EntryNotFoundException("Book by autor surname " + surname + " and title " + title + " not found");
        }
    }


    public Book updateBook(Book book) {
        Optional<Book> optBook = bookRepository.findById(book.getId());
        Book bookToUpdate = optBook.orElseThrow(() -> new EntryNotFoundException("Book with id " + book.getId() + " not exists"));
        CheckAndSaveAuthor(book);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthors(book.getAuthors());
        bookRepository.save(bookToUpdate);
        return bookToUpdate;
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    private void CheckAndSaveAuthor(Book book) {
        Set<Author> authors = book.getAuthors();
        for (Author author: authors) {
            if (author.getSurname() == null || author.getSurname().isBlank()) throw new NotValidRequestException("Surname of author for book title " + book.getTitle() + " is blank or null");
            Author savedBefore = authorRepository.findAuthorBySurname(author.getSurname());
            if (savedBefore != null) {
                author.setId(savedBefore.getId());
            } else {
                Author saved = authorRepository.save(author);
                author.setId(saved.getId());
            }
        }
    }
}

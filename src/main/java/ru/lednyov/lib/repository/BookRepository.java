package ru.lednyov.lib.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lednyov.lib.domain.Book;

import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {

    Iterable<Book> findBooksByTitle(String title);

    @Query(value = "select * from books join books_authors on b_id = book_b_id join authors on a_id = authors_a_id where surname = :surname",
            nativeQuery = true)
    Iterable<Book> findBooksByAuthorSurname(@Param("surname") String surname);

    @Query(value = "select * from books join books_authors on b_id = book_b_id join authors on a_id = authors_a_id where surname = :surname and title = :title",
            nativeQuery = true)
    Iterable<Book> findBooksByAuthorSurnameAndTitle(@Param("surname") String surname, @Param("title") String title);


}
package ru.lednyov.lib.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lednyov.lib.domain.Author;

import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<Author, UUID> {

    public Author findAuthorBySurname(String surname);
}

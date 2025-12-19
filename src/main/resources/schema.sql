drop table if exists authors;

create table authors (
    a_id uuid not null primary key ,
    surname varchar(255) not null
);

drop table if exists books;

create table books (
    b_id uuid not null primary key ,
    title varchar(255) not null
);

drop table if exists books_authors;

create table books_authors (
    authors_id uuid not null ,
    book_id uuid not null,
    primary key (authors_id, book_id)
);

alter table if exists books_authors
add constraint fk1 foreign key (authors_id) references authors;

alter table if exists books_authors
add constraint fk2 foreign key (book_id) references books;
package ru.gb.springbootlesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springbootlesson3.entity.Book;

import java.util.List;
import java.util.Optional;

public interface JpaBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    Optional<Book> deleteById(long id);
    Book save(Book book);
    Optional<Book> findByName(String name);
    List<Book> findAll();
}
package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.repository.JpaBookRepository;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final JpaBookRepository jpaBookRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void createDatabase(){
        jpaBookRepository.save(new Book("Война и мир"));
        jpaBookRepository.save(new Book("Мастер и Маргарита"));
        jpaBookRepository.save(new Book("Приключения Буратино"));
        jpaBookRepository.save(new Book("Сердце Пармы"));
    }

    public Book findBook(long id) {
        Book book = jpaBookRepository.findById(id).orElse(null);
        if (book == null) {
            log.info("Не удалось найти книгу с id=" + id);
            throw new NoSuchElementException("Не удалось найти книгу с id=" + id);
        }
        return book;
    }

    public Book deleteBook(long id) {
        Book book = jpaBookRepository.findById(id).orElse(null);
        if (book == null) {
            log.info("Не удалось найти книгу с id=" + id);
            throw new NoSuchElementException("Не удалось найти книгу с id=" + id);
        } else {
            jpaBookRepository.deleteById(id);
        }
        return book;
    }

    public Book addBook(String name) throws NoPermissionException {
        Book book = jpaBookRepository.findByName(name).orElse(null);
        if (book != null) {
            log.info("Уже имеется книга с name=" + name);
            throw new NoPermissionException("Не удалось добавить книгу с name=" + name);
        }
        return jpaBookRepository.save(new Book(name));
    }

    public List<Book> getBookList(){
        return jpaBookRepository.findAll();
    }

}
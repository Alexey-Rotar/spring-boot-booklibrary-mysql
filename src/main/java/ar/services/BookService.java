package ar.services;

import ar.entity.Book;
import ar.repository.JpaBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final JpaBookRepository jpaBookRepository;

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
package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.repository.BookRepository;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book findBook(long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            log.info("Не удалось найти книгу с id=" + id);
            throw new NoSuchElementException("Не удалось найти книгу с id=" + id);
        }
        return book;
    }

    public Book deleteBook(long id) {
        Book book = bookRepository.deleteBook(id);
        if (book == null) {
            log.info("Не удалось найти книгу с id=" + id);
            throw new NoSuchElementException("Не удалось найти книгу с id=" + id);
        }
        return book;
    }

    public Book addBook(String name) throws NoPermissionException {
        Book book = bookRepository.addBook(name);
        if (book == null) {
            log.info("Уже имеется книга с name=" + name);
            throw new NoPermissionException("Не удалось добавить книгу с name=" + name);
        }
        return book;
    }

    public List<Book> getBookList(){
        return bookRepository.getBookList();
    }

}

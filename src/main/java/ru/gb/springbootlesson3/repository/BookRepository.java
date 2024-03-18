package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Book;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private List<Book> list = new ArrayList<>();

    public BookRepository() {
        list.add(new Book("Война и мир"));
        list.add(new Book("Мастер и Маргарита"));
        list.add(new Book("Приключения Буратино"));
    }

    /**
     * Поиск книги по id
     * @param id id книги
     * @return книга Book или null
     */
    public Book findById(long id){
        return list.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск книги по имени
     * @param name имя книги
     * @return найденная книга Book или null
     */
    public Book findByName(String name){
        return list.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Удаление книги с указанным id
     * @param id id книги
     * @return книга Book или null
     */
    public Book deleteBook(long id){
        Book book = findById(id);
        list.remove(book);
        return book;
    }

    /**
     * Добавление книги
     * @param name имя книги
     * @return добавленная книга Book или null, если не была добавлена
     */
    public Book addBook(String name){
        Book book = findByName(name);
        if (book == null) {
            book = new Book(name);
            list.add(book);
            return book;
        } else {
            return null;
        }
    }
}

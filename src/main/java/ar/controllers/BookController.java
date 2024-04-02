package ar.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.entity.Book;
import ar.services.BookService;

import javax.naming.NoPermissionException;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable long id){
        log.info("Поступил запрос информации о книге: bookId={}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findBook(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteById(@PathVariable long id){
        log.info("Поступил запрос на удаление книги: bookId={}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteBook(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> addByName(@RequestParam String name){
        log.info("Поступил запрос на добавление книги: bookName={}", name);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.addBook(name));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e){
            return ResponseEntity.status(409).build(); // 409 = "Conflict"
        }
    }

}

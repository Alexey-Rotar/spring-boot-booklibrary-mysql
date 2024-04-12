package ar.controllers;

import ar.entity.Issue;
import ar.entity.Reader;
import ar.services.IssueService;
import ar.services.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import javax.naming.NoPermissionException;

@Slf4j
@RestController
@RequestMapping("reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private IssueService issueService;

    @GetMapping("{id}")
    public ResponseEntity<Reader> getById(@PathVariable long id){
        log.info("Поступил запрос информации о читателе: readerId={}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.findReader(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Reader> deleteById(@PathVariable long id){
        log.info("Поступил запрос на удаление читателя: readerId={}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.deleteReader(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Reader> addByName(@RequestParam String name){
        log.info("Поступил запрос на добавление читателя: bookName={}", name);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(readerService.addReader(name));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e){
            return ResponseEntity.status(409).build();
        }
    }

    //GET /reader/{id}/issue - вернуть список всех выдачей для данного читателя
    @GetMapping("reader/{id}/issue")
    public ResponseEntity<List<Issue>> getAllIssues(@PathVariable long id){
        log.info("Поступил запрос на список выданных книг читателю: readerId={}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueListByReaderId(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

}
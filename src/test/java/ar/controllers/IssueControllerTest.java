package ar.controllers;

import ar.entity.Book;
import ar.entity.Issue;
import ar.entity.Reader;
import ar.repository.JpaBookRepository;
import ar.repository.JpaIssueRepository;
import ar.repository.JpaReaderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class IssueControllerTest {

    @Autowired
    JpaReaderRepository jpaReaderRepository;

    @Autowired
    JpaBookRepository jpaBookRepository;

    @Autowired
    JpaIssueRepository jpaIssueRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCreateIssue() {
        // сохраняю в репозиторий читателя и книгу
        Reader testReader = jpaReaderRepository.save(new Reader("TEST-READER-1"));
        Book testBook = jpaBookRepository.save(new Book("TEST-BOOK-1"));

        // создаю экземпляры читателя и книги для последующего запроса
        Reader requestReader = new Reader();
        Book requestBook = new Book();

        // устанавливаю ID читателя и книги, имеющихся в репозитории, читателю и книги для запроса
        requestReader.setId(testReader.getId());
        requestBook.setId(testBook.getId());

        // формирую тело запроса
        IssueRequest issueRequest = new IssueRequest();
        issueRequest.setReader(requestReader);
        issueRequest.setBook(requestBook);

        Issue responseIssue = webTestClient.post()
                .uri("issue/")
                .body(issueRequest, IssueRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Issue.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseIssue);
//        Assertions.assertEquals(responseIssue.getReader().getName(), testReader.getName());
//        Assertions.assertEquals(responseIssue.getBook().getName(), testBook.getName());
    }

}
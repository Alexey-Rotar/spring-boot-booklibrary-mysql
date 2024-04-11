package ar.controllers;

import ar.entity.Book;
import ar.repository.JpaBookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    JpaBookRepository jpaBookRepository;

    @Autowired
    WebTestClient webTestClient;

//    private Book testBook1, testBook2;
//    @BeforeAll
//    static void setup() {
//        Book testBook1 = jpaBookRepository.save(new Book("TEST-BOOK-1"));
//        Book testBook2 = new Book("TEST-BOOK-2");
//    }

    @Test
    void testGetByIdOk() {
        Book testBook1 = jpaBookRepository.save(new Book("TEST-BOOK"));
        long existentId = testBook1.getId();

        Book responseBook = webTestClient.get()
                .uri("book/" + existentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(responseBook.getId(), testBook1.getId());
        Assertions.assertEquals(responseBook.getName(), testBook1.getName());
    }

    @Test
    void testGetByIdNotFound() {
        Book testBook2 = new Book("TEST-BOOK-2");

        long nonExistentId = testBook2.getId();

        webTestClient.get()
                .uri("book/" + nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdOk() {
        Book testBook1 = jpaBookRepository.save(new Book("TEST-BOOK-1"));
        long existentId = testBook1.getId();

        Book responseBook = webTestClient.delete()
                .uri("book/" + existentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(responseBook, testBook1);
    }

    @Test
    void testDeleteByIdNotFound() {
        Book testBook2 = new Book("TEST-BOOK-2");

        long nonExistentId = testBook2.getId();

        webTestClient.delete()
                .uri("book/" + nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testAddByName() {
        String testBookName = "TEST-BOOK";

        Book responseBook = webTestClient.post()
                .uri("book?name=" + testBookName)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(responseBook.getName(), testBookName);
    }
}
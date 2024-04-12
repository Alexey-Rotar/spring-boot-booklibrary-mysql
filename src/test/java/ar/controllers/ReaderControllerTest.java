package ar.controllers;

import ar.entity.Reader;
import ar.repository.JpaReaderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ReaderControllerTest {

    @Autowired
    JpaReaderRepository jpaReaderRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testGetByIdOk() {
        Reader testReader1 = jpaReaderRepository.save(new Reader("TEST-READER"));
        long existentId = testReader1.getId();

        Reader responseReader = webTestClient.get()
                .uri("reader/" + existentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseReader);
        Assertions.assertEquals(responseReader.getId(), testReader1.getId());
        Assertions.assertEquals(responseReader.getName(), testReader1.getName());
    }

    @Test
    void testGetByIdNotFound() {
        Reader testReader2 = new Reader("TEST-READER-2");

        long nonExistentId = testReader2.getId();

        webTestClient.get()
                .uri("reader/" + nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdOk() {
        Reader testReader1 = jpaReaderRepository.save(new Reader("TEST-READER-1"));
        long existentId = testReader1.getId();

        Reader responseReader = webTestClient.delete()
                .uri("reader/" + existentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseReader);
        Assertions.assertEquals(responseReader, testReader1);
    }

    @Test
    void testDeleteByIdNotFound() {
        Reader testReader2 = new Reader("TEST-READER-2");

        long nonExistentId = testReader2.getId();

        webTestClient.delete()
                .uri("reader/" + nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testAddByName() {
        String testReaderName = "TEST-READER";

        Reader responseReader = webTestClient.post()
                .uri("reader?name=" + testReaderName)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Reader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseReader);
        Assertions.assertEquals(responseReader.getName(), testReaderName);
    }

//    @Test
//    void getAllIssues() {
//    }
}
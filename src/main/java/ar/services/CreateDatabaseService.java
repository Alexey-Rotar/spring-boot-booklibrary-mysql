package ar.services;

import ar.entity.Book;
import ar.entity.Consumer;
import ar.entity.Issue;
import ar.entity.Reader;
import ar.repository.JpaBookRepository;
import ar.repository.JpaConsumerRepository;
import ar.repository.JpaIssueRepository;
import ar.repository.JpaReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDatabaseService {
    private final JpaIssueRepository jpaIssueRepository;
    private final JpaReaderRepository jpaReaderRepository;
    private final JpaBookRepository jpaBookRepository;
    private final JpaConsumerRepository jpaConsumerRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void createDatabase(){
        jpaBookRepository.save(new Book("Война и мир"));
        jpaBookRepository.save(new Book("Мастер и Маргарита"));
        jpaBookRepository.save(new Book("Приключения Буратино"));
        jpaBookRepository.save(new Book("Сердце Пармы"));
        jpaBookRepository.save(new Book("Два капитана"));

        jpaReaderRepository.save(new Reader("Алексей"));
        jpaReaderRepository.save(new Reader("Сергей"));
        jpaReaderRepository.save(new Reader("Татьяна"));
        jpaReaderRepository.save(new Reader("Ирина"));

        Book book = jpaBookRepository.save(new Book("Преступление и наказание"));
        Reader reader = jpaReaderRepository.save(new Reader("Олег"));
        jpaIssueRepository.save(new Issue(reader, book));

        jpaConsumerRepository.save(new Consumer("admin", "pwd", "admin"));
        jpaConsumerRepository.save(new Consumer("user", "pwd", "user"));
    }

}
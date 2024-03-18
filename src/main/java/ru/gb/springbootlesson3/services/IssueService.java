package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.controllers.IssueRequest;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.repository.BookRepository;
import ru.gb.springbootlesson3.repository.IssueRepository;
import ru.gb.springbootlesson3.repository.ReaderRepository;

import javax.naming.NoPermissionException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor // означает, что нужно создать конструктор из всех присутствующих аргументов (из всех трех)
@Service
public class IssueService {

    // максимальное кол-во книг для выдачи читателю - параметр из конфига
    @Value("${application.issue.max-allowed-books}")
    private String MAX_BOOKS_NUMBER;

    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;
    private final ReaderRepository readerRepository;

    public Issue createIssue(IssueRequest request) throws NoPermissionException {
        if (bookRepository.findById(request.getBookId()) == null){
            log.info("Не удалось найти книгу с id=" + request.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу с id=" + request.getBookId());
        }
        if (readerRepository.findById(request.getReaderId()) == null){
            log.info("Не удалось найти читателя с id " + request.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя с id=" + request.getReaderId());
        }
        if (issueRepository.bookCountByReaderId(request.getReaderId()) >= Long.parseLong(MAX_BOOKS_NUMBER)){
            log.info("Читатель с id=" + request.getReaderId() + " имеет на руках максимум книг");
            throw new NoPermissionException("Нельзя выдать книги читателю с id=" + request.getReaderId());
        }

        Issue issue = new Issue(request.getReaderId(), request.getBookId());
        issueRepository.createIssue(issue);
        return issue;
    }

    public Issue findIssue(long id) {
        Issue issue = issueRepository.findById(id);
        if (issue == null) {
            log.info("Не удалось найти выдачу с id=" + id);
            throw new NoSuchElementException("Не удалось найти выдачу с id=" + id);
        }
        return issue;
    }

    public List<Issue> findIssuesByReaderId(long id) {
        return issueRepository.getIssueListByReaderId(id);
    }

    public Issue closeIssueById(long issueId) {
        Issue issue = findIssue(issueId);
        issue.setReturned_at(LocalDateTime.now());
        return issue;
    }
}

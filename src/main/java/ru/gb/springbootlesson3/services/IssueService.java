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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor // означает, что нужно создать конструктор из всех присутствующих аргументов (из всех трех)
@Service
public class IssueService {

    // максимальное кол-во книг для выдачи читателю - параметр из конфига
    @Value("${application.issue.max-allowed-books:1}") // дефолтное значение 1
    private Long MAX_ALLOWED_BOOKS;

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
        if (issueRepository.notClosedIssuesCountByReaderId(request.getReaderId()) >= MAX_ALLOWED_BOOKS){
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

    public List<Issue> getIssueListByReaderId(long id) {
        return issueRepository.getIssueListByReaderId(id);
    }

    public Issue closeIssueById(long issueId) {
        Issue issue = findIssue(issueId);
        issue.setReturned_at(LocalDateTime.now());
        return issue;
    }

    public List<Issue> getIssueList(){
        return issueRepository.getIssueList();
    }

    /**
     * Получение списка ID невозвращенных книг по ID читателя
     * @param id ID читателя
     * @return список ID книг List<Long>
     */
    public List<Long> getNotReturnedBooksByReaderId(long id){
        List<Issue> issueList = issueRepository.notClosedIssuesByReaderId(id);
        List<Long> idList = new ArrayList<>();
        for (Issue issue : issueList) {
            idList.add(issue.getIdBook());
        }
        return idList;
    }

}

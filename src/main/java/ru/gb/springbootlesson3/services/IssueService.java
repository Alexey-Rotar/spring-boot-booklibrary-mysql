package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.controllers.IssueRequest;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.repository.*;

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

    private final JpaIssueRepository jpaIssueRepository;
    private final JpaReaderRepository jpaReaderRepository;
    private final JpaBookRepository jpaBookRepository;

    public Issue createIssue(IssueRequest request) throws NoPermissionException {
        if (jpaBookRepository.findById(request.getBookId()).isEmpty()){
            log.info("Не удалось найти книгу с id=" + request.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу с id=" + request.getBookId());
        }
        if (jpaReaderRepository.findById(request.getReaderId()).isEmpty()){
            log.info("Не удалось найти читателя с id " + request.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя с id=" + request.getReaderId());
        }
        // подсчёт количества не закрытых выдачей по ID читателя
        if (getIssueList().stream()
                .filter(e -> e.getIdReader() == request.getReaderId() && e.getReturned_at() == null)
                .count() >= MAX_ALLOWED_BOOKS){
            log.info("Читатель с id=" + request.getReaderId() + " имеет на руках максимум книг");
            throw new NoPermissionException("Нельзя выдать книги читателю с id=" + request.getReaderId());
        }
        Issue issue = new Issue(request.getReaderId(), request.getBookId());
        return jpaIssueRepository.save(issue);
    }

    public Issue findIssue(long id) {
        Issue issue = jpaIssueRepository.findById(id).orElse(null);
        if (issue == null) {
            log.info("Не удалось найти выдачу с id=" + id);
            throw new NoSuchElementException("Не удалось найти выдачу с id=" + id);
        }
        return issue;
    }

    public List<Issue> getIssueListByReaderId(long id) {
        List<Issue> list = getIssueList();
        return list.stream()
                .filter(e -> e.getIdReader() == id)
                .toList();
    }

    public Issue closeIssueById(long issueId) {
        Issue issue = findIssue(issueId);
        issue.setReturned_at(LocalDateTime.now());
        return jpaIssueRepository.save(issue);
    }

    public List<Issue> getIssueList(){
        return jpaIssueRepository.findAll();
    }

    /**
     * Получение списка ID невозвращенных книг по ID читателя
     * @param id ID читателя
     * @return список ID книг List<Long>
     */
    public List<Long> getNotReturnedBooksByReaderId(long id){
        List<Issue> list = getIssueList();
        // получение списка (фильтр) не закрытых выдачей по ID читателя
        List<Issue> issueList = list.stream()
                .filter(e -> e.getIdReader() == id && e.getReturned_at() == null)
                .toList();
        List<Long> idList = new ArrayList<>();
        for (Issue issue : issueList) {
            idList.add(issue.getIdBook());
        }
        return idList;
    }

}

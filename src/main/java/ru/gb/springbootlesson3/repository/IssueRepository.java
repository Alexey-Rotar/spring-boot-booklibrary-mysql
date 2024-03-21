package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Issue;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IssueRepository {
    private List<Issue> list = new ArrayList<>();

    public void createIssue(Issue issue){
        list.add(issue);
    }

    /**
     * Поиск выдачи по id
     * @param id id выдачи
     * @return выдача Issue
     */
    public Issue findById(long id){
        return list.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск выдачи по id читателя (есть ли хоть одна)
     * @param id id читателя
     * @return выдача Issue
     */
    public Issue findByReaderId(long id) {
        return list.stream()
                .filter(e -> e.getIdReader() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск выдачей для читателя с указанным id
     * @param id id читателя
     * @return список выдачей List<Issue>
     */
    public List<Issue> getIssueListByReaderId(long id) {
        return list.stream()
                .filter(e -> e.getIdReader() == id)
                .toList();
    }

    /**
     * Подсчёт количества не закрытых выдачей по ID читателя
     * @param id id читателя
     * @return количество выдачей
     */
    public long notClosedIssuesCountByReaderId(long id) {
        return list.stream()
                .filter(e -> e.getIdReader() == id && e.getReturned_at() == null)
                .count();
    }

    /**
     * Получение списка всех выдачей
     * @return список выдачей List<Issue>
     */
    public List<Issue> getIssueList(){
        return List.copyOf(list);
    }

    /**
     * Получение списка не закрытых выдачей по ID читателя
     * @param id id читателя
     * @return список выдачей
     */
    public List<Issue> notClosedIssuesByReaderId(long id) {
        return list.stream()
                .filter(e -> e.getIdReader() == id && e.getReturned_at() == null)
                .toList();
    }

}
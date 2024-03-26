package ru.gb.springbootlesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springbootlesson3.entity.Issue;

import java.util.List;
import java.util.Optional;

public interface JpaIssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findById(Long id);
    Issue save(Issue issue);
    List<Issue> findAll();
}

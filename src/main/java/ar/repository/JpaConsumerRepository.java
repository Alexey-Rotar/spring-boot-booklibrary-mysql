package ar.repository;

import ar.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaConsumerRepository extends JpaRepository<Consumer, Long> {
    Optional<Consumer> findByLogin(String login);

    Consumer save(Consumer consumer);
}

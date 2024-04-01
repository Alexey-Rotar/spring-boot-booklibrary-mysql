package ar.services;

import ar.entity.Consumer;
import ar.repository.JpaConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {
    private final JpaConsumerRepository jpaConsumerRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void createDatabase(){
        jpaConsumerRepository.save(new Consumer("admin", "pwd", "admin"));
        jpaConsumerRepository.save(new Consumer("user", "pwd", "user"));
    }

    public Consumer addConsumer(Consumer consumer){
        return jpaConsumerRepository.save(consumer);
    }
}

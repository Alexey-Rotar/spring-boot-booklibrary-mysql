package ar.services;

import ar.entity.Consumer;
import ar.repository.JpaConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {
    private final JpaConsumerRepository jpaConsumerRepository;

    public Consumer addConsumer(Consumer consumer){
        return jpaConsumerRepository.save(consumer);
    }
}

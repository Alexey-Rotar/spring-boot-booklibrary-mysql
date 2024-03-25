package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.repository.JpaReaderRepository;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReaderService {
    private final JpaReaderRepository jpaReaderRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void createDatabase(){
        jpaReaderRepository.save(new Reader("Алексей"));
        jpaReaderRepository.save(new Reader("Сергей"));
        jpaReaderRepository.save(new Reader("Татьяна"));
        jpaReaderRepository.save(new Reader("Ирина"));
    }

    public Reader findReader(long id) {
        Reader reader = jpaReaderRepository.findById(id).orElse(null);
        if (reader == null) {
            log.info("Не удалось найти читателя с id=" + id);
            throw new NoSuchElementException("Не удалось найти читателя с id=" + id);
        }
        return reader;
    }

    public Reader deleteReader(long id) {
        Reader reader = jpaReaderRepository.findById(id).orElse(null);
        if (reader == null) {
            log.info("Не удалось найти читателя с id=" + id);
            throw new NoSuchElementException("Не удалось найти читателя с id=" + id);
        } else {
            jpaReaderRepository.deleteById(id);
        }
        return reader;
    }

    public Reader addReader(String name) throws NoPermissionException {
        Reader reader = jpaReaderRepository.findByName(name).orElse(null);
        if (reader != null) {
            log.info("Уже имеется читатель с name=" + name);
            throw new NoPermissionException("Не удалось добавить читателя с name=" + name);
        }
        return jpaReaderRepository.save(new Reader(name));
    }

    public List<Reader> getReaderList(){
        return jpaReaderRepository.findAll();
    }

}

package ru.gb.springbootlesson3.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.springbootlesson3.entity.Reader;
import ru.gb.springbootlesson3.repository.ReaderRepository;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public Reader findReader(long id) {
        Reader reader = readerRepository.findById(id);
        if (reader == null) {
            log.info("Не удалось найти читателя с id " + id);
            throw new NoSuchElementException("Не удалось найти читателя с id " + id);
        }
        return reader;
    }

    public Reader deleteReader(long id) {
        Reader reader = readerRepository.deleteReader(id);
        if (reader == null) {
            log.info("Не удалось найти читателя с id " + id);
            throw new NoSuchElementException("Не удалось найти читателя с id " + id);
        }
        return reader;
    }

    public Reader addReader(String name) throws NoPermissionException {
        Reader reader = readerRepository.addReader(name);
        if (reader == null) {
            log.info("Уже имеется читатель с name " + name);
            throw new NoPermissionException("Не удалось добавить читателя с name " + name);
        }
        return reader;
    }

    public List<Reader> getReaderList(){
        return readerRepository.getReaderList();
    }

}

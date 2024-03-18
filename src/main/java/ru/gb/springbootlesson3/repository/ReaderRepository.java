package ru.gb.springbootlesson3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Reader;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReaderRepository {
    private List<Reader> list = new ArrayList<>();

    public ReaderRepository() {
        list.add(new Reader("Костя"));
        list.add(new Reader("Василий"));
        list.add(new Reader("Семен"));
    }

    /**
     * Поиск читателя по id
     * @param id id читателя
     * @return читатель Reader или null
     */
    public Reader findById(long id){
        return list.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск читателя по имени
     * @param name имя читателя
     * @return читатель Reader или null
     */
    public Reader findByName(String name){
        return list.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Удаление читателя с укащзанным id
     * @param id id читателя
     * @return удаленный читатель Reader
     */
    public Reader deleteReader(long id){
        Reader reader = findById(id);
        list.remove(reader);
        return reader;
    }

    /**
     * Добавление читателя
     * @param name имя читателя
     * @return добавленный читатель Reader или null, если не добавлен
     */
    public Reader addReader(String name){
        Reader reader = findByName(name);
        if (reader == null) {
            reader = new Reader(name);
            list.add(reader);
            return reader;
        } else {
            return null;
        }
    }

}

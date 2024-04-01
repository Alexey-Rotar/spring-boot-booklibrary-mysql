package ar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.entity.Reader;

import java.util.List;
import java.util.Optional;

public interface JpaReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findById(Long id);
    Optional<Reader> deleteById(long id);
    Reader save(Reader reader);
    Optional<Reader> findByName(String name);
    List<Reader> findAll();
}

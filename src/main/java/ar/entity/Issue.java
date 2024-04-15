package ar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "issues")
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "id")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id")
    private Book book;

    @Column
    private LocalDateTime issued_at;

    @Column
    private LocalDateTime returned_at;

    public Issue(Reader reader, Book book){
        this.reader = reader;
        this.book = book;
        issued_at = LocalDateTime.now();
        returned_at = null;
    }

}

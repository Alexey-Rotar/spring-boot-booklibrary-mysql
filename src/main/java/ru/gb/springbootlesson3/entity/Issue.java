package ru.gb.springbootlesson3.entity;

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

    @Column
    private long idReader;

    @Column
    private long idBook;

    @Column
    private LocalDateTime issued_at;

    @Column
    private LocalDateTime returned_at;
    public Issue(long idReader, long idBook){
        this.idBook = idBook;
        this.idReader = idReader;
        issued_at = LocalDateTime.now();
        returned_at = null;
    }

}

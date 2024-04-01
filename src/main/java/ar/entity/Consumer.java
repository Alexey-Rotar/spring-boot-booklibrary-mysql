package ar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

// класс для создания пользователей системы (User лучше не использовать во избежание путаницы с User из SpringSecurity)
@Entity
@Data
@Table(name = "consumers")
@NoArgsConstructor
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    public Consumer(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}

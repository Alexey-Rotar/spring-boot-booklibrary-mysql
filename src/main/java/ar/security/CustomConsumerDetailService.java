package ar.security;

import ar.entity.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ar.repository.JpaConsumerRepository;

import java.util.List;

// собственный класс для обработки запросов и разграничения доступа
// при реализации это интерфейса он будет использоваться вместо дефолтного поведения от Spring
@Component
@RequiredArgsConstructor
public class CustomConsumerDetailService implements UserDetailsService {
    private final JpaConsumerRepository jpaConsumerRepository;

    // UserDetails - класс из Spring Security, содержащий данные о пользователе (имя, пароль...)
    // переопределение метода, возвращающего UserDetails чтобы с ней потом работал Spring Security
    // заменяет дефолтное поведение Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // поиск пользователя по логину
        Consumer consumer = jpaConsumerRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь " + username + " не найден"));
        return new User(consumer.getLogin(), consumer.getPassword(), List.of(
                new SimpleGrantedAuthority(consumer.getRole())
        ));
    }
}

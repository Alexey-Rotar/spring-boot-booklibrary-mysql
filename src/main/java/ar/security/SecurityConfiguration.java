package ar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests(registry -> registry
                                // в методе requestMatchers указывается какие запросы как обрабатывать
                                // описывается разграничение по правам доступа

                                // ресурсы "ui/**" доступны для любого, кто авторизовался из перечисленых
                                .requestMatchers("ui/**").hasAnyAuthority("user", "admin")

//                        // доступ к ресурсам "issue/**" для того кто авторизовался как указано в скобках, т.е. admin
//                        .requestMatchers("issue/**").hasAuthority("admin")
//
//                        // "reader/**" для любого авторизовавшегося
//                        .requestMatchers("reader/**").authenticated()
//
//                        // доступ к ресурсам "book/**" разрешен для всех
//                        .requestMatchers("book/**").permitAll()
//
//                        // доступ ко всем остальным ресурсам запрещен
//                        .anyRequest().denyAll()
                                       
                        // доступ ко всем остальным ресурсам разрешен
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults()) // дефолтная форма аутентификации
                .csrf(AbstractHttpConfigurer::disable) // отключение защиты CSRF (для работы POST, DELETE, PUT)
                .build();
    }

}
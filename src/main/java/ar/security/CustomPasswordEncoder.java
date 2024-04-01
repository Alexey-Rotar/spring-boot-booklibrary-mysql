package ar.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// класс, отвечающий за кодирование и сравнение паролей
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // здесь должны шифроваться данные, rawPassword - то, что пользователь ввел в форму авторизации
        return String.valueOf(rawPassword); // для простоты возвращается без изменений, не зашифровано, та же строка
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //сравниваются пароли, то что ввел пользователь и то что в базе
        return encode(rawPassword).equals(encodedPassword);
    }
}
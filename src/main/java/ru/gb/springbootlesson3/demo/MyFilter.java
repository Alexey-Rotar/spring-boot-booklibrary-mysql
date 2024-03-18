package ru.gb.springbootlesson3.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j // логирование от Lombok
@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // если тип servletRequest совпадает с HttpServletRequest, то сразу производится каст
        // и сохраняется в переменную httpServletRequest
        if (servletRequest instanceof HttpServletRequest httpServletRequest){
            String uri = httpServletRequest.getRequestURI();
            log.info("Входящий запрос: {}", uri); // синтаксис ломбок, в {} подставляется uri

            if (uri.startsWith("/admin")){
                ((HttpServletResponse) servletResponse).sendError(403); // 403 - код ошибки
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

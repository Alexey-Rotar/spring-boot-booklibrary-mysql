package ru.gb.springbootlesson3.demo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class MyBean {

//    @PostConstruct // метод сработает после создания бина
//    public void postConstruct(){
//        log.info("post construct");
//    }

//    @PreDestroy // перед уничтожением бина (не сработает,  если тип бина prototype)
//    public void preDestroy(){
//        log.info("pre destroy");
//    }

    /*
    @EventListener(ContextRefreshedEvent.class) // слушатель события
    public void myEvent1(){
        return; // что-то можно сделать, когда все бины созданы
    }

    // или так:

    @EventListener() // слушатель события
    public void myEvent2(ContextRefreshedEvent event){
        log.info("Бины созданы");
        // что-то можно сделать, когда все бины созданы
    }
     */


//    @EventListener() // слушатель события
//    public void myEvent(MyEvent event){
//        log.info("Поймал событие");
//    }
}

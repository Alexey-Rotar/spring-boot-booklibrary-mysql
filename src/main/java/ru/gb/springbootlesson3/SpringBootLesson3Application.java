package ru.gb.springbootlesson3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gb.springbootlesson3.demo.SecondBean;

@SpringBootApplication
public class SpringBootLesson3Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootLesson3Application.class, args);

		context.getBean(SecondBean.class).postConstruct();

		/*
		СЛОИ:
		1. Контроллеры (controllers, api) - принимает запрос, возвращает ответ
		2. Сервисы (services) - логика, как отреагировать на конкретный сигнал, возвращает ответ контроллеру
		3. Репозитории (repository, DAO - data access object) - подключение к удаленным ресурсам (БД)
		4. Сущности (entity, model) - классы, описывающие тип данных

		/book/**
		/reader/**
		/issue/**
		 */
	}

}

package ru.gb.springbootlesson3.controllers;

import lombok.Data;
import ru.gb.springbootlesson3.entity.Book;
import ru.gb.springbootlesson3.entity.Reader;

@Data
public class IssueRequest {
    private Reader reader;
    private Book book;
}

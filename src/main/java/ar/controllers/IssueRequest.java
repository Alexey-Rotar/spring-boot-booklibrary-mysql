package ar.controllers;

import lombok.Data;
import ar.entity.Book;
import ar.entity.Reader;

@Data
public class IssueRequest {
    private Reader reader;
    private Book book;
}

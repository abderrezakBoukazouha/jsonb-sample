package org.winside.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.winside.models.Book;
import org.winside.services.BookService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<Book>> getBooksByIsbn(@RequestParam("isbn") String isbn) {
        return ResponseEntity.ok(bookService.retrieveAllBooksByIsbn(isbn));
    }
}

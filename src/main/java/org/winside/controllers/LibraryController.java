package org.winside.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.winside.entities.LibraryEntity;
import org.winside.models.Book;
import org.winside.models.Library;
import org.winside.services.LibraryService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    
    private final LibraryService libraryService;
    
    @PostMapping
    public ResponseEntity<Void> saveLibrary(@RequestBody Library library) {
        libraryService.save(library);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<LibraryEntity>> getLibrary() {
        return ResponseEntity.ok(libraryService.retreiveAll());
    }
    
    @GetMapping("book")
    public ResponseEntity<List<Book>> getBooksByIsbn(@RequestParam("isbn") String isbn) throws JsonProcessingException {
        return ResponseEntity.ok(libraryService.retreiveAllBooksByIsbn(isbn));
    }
}
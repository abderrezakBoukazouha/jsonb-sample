package org.winside.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.winside.entities.LibraryEntity;
import org.winside.models.Library;
import org.winside.services.LibraryService;

@Controller
@AllArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    
    private final LibraryService libraryService;
    
    @PostMapping
    public ResponseEntity<LibraryEntity> saveLibrary(@RequestBody Library library) {
        return ResponseEntity.ok(libraryService.save(library));
    }
}
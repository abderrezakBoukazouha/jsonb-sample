package org.winside.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.winside.entities.LibraryEntity;
import org.winside.models.Library;
import org.winside.repositories.LibraryRepository;

@Service
@AllArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;
    
    public LibraryEntity save(Library library) {
         
        return libraryRepository.saveAndFlush(LibraryEntity.builder().library(library).build());
    }
}

package org.winside.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.winside.converters.BookConverter;
import org.winside.entities.LibraryEntity;
import org.winside.models.Book;
import org.winside.models.Library;
import org.winside.repositories.LibraryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    ObjectMapper mapper;
    
    public void save(Library library) {
         libraryRepository.saveAndFlush(LibraryEntity.builder().library(library).build());
    }

    public List<LibraryEntity> retreiveAll() {
        return libraryRepository.findAll();
    }
    
    public List<Book> retreiveAllBooksByIsbn(String isbn) {
        List<String> jsonResponses =  libraryRepository.findBookByIsbn(isbn);
        return jsonResponses.stream().map(s -> {
            try {
               return mapper.readValue(s, Book.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}

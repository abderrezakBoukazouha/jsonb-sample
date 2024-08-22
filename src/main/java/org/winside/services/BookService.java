package org.winside.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.winside.exceptions.BookNotFoundException;
import org.winside.models.Book;
import org.winside.repositories.LibraryRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {
    private final LibraryRepository libraryRepository;

    private final ObjectMapper mapper;

    public List<Book> retrieveAllBooksByIsbn(String isbn) {
        List<String> jsonResponses = libraryRepository.findBookByIsbn(isbn);
        List<Book> books =  jsonResponses.stream().map(jsonResponse -> {
            try {
                return mapper.readValue(jsonResponse, Book.class);
            } catch (JsonProcessingException e) {
                throw new BookNotFoundException(e.getMessage());
            }
        }).toList();

        if(books.isEmpty()) {
            throw new BookNotFoundException("Book not found for this isbn : %s".formatted(isbn));
        }
        return books;
    }
}

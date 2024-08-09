package org.winside.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.winside.models.Book;

@Converter
@AllArgsConstructor
@Component
public class BookConverter implements AttributeConverter<Book, String> {

    private final ObjectMapper objectMapper;


    @Override
    public String convertToDatabaseColumn(final Book book) {
        try {
            final String bookAsString = this.objectMapper.writeValueAsString(book);
            System.out.printf("%s transformed successfully into %s%n", Book.class.getSimpleName(),
                    String.class.getSimpleName());
            return bookAsString;
        } catch (final JsonProcessingException exception) {
            System.out.printf("Unable to convert %s to %s: %s", Book.class.getSimpleName(),
                    String.class.getSimpleName(), exception.getMessage());
            return null;
        }
    }

    @Override
    public Book convertToEntityAttribute(final String book) {
        try {
            final Book bookAsString = this.objectMapper.readValue(book, Book.class);
            System.out.printf("%s transformed successfully into %s%n", String.class.getSimpleName(), Book.class.getSimpleName());
            return bookAsString;
        } catch (final JsonProcessingException exception) {
            System.out.printf("Unable to convert %s to %s: %s", String.class.getSimpleName(),
                    Book.class.getSimpleName(), exception.getMessage());
            return null;
        }
    }
}

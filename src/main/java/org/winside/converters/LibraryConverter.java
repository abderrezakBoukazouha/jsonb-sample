package org.winside.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import org.winside.models.Library;

@Converter
@AllArgsConstructor
public class LibraryConverter implements AttributeConverter<Library, String> {

    private final ObjectMapper objectMapper;


    @Override
    public String convertToDatabaseColumn(final Library library) {
        try {
            final String bookAsString = this.objectMapper.writeValueAsString(library);
            System.out.printf("%s transformed successfully into %s%n", Library.class.getSimpleName(),
                    String.class.getSimpleName());
            return bookAsString;
        } catch (final JsonProcessingException exception) {
            System.out.printf("Unable to convert %s to %s: %s", Library.class.getSimpleName(),
                    String.class.getSimpleName(), exception.getMessage());
            return null;
        }
    }

    @Override
    public Library convertToEntityAttribute(final String library) {
        try {
            final Library stringAsLibrary = this.objectMapper.readValue(library, Library.class);
            System.out.printf("%s transformed successfully into %s%n", String.class.getSimpleName(), Library.class.getSimpleName());
            return stringAsLibrary;
        } catch (final JsonProcessingException exception) {
            System.out.printf("Unable to convert %s to %s: %s", String.class.getSimpleName(),
                    Library.class.getSimpleName(), exception.getMessage());
            return null;
        }
    }
}

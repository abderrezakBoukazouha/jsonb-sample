package org.winside.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

import java.time.LocalDate;
import java.util.List;

@With
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String bookId;

    private String title;

    private String isbn;

    private String genre;

    private LocalDate publicationDate;

    private Integer numberOfPages;

    private List<Author> authors;
}

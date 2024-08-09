package org.winside.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

import java.util.List;

@With
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Library {

    private List<Book> books;
}

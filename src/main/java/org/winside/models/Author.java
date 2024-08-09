package org.winside.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

import java.util.List;

@With
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    
    private String authorId;

    private String firstName;

    private String lastName;

    private Integer birthYear;

    private String nationality;

    private List<String> awards;

    private Boolean isAlive;
}

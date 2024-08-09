package org.winside.repositories;


import jakarta.persistence.Convert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.winside.converters.BookConverter;
import org.winside.entities.LibraryEntity;
import org.winside.models.Book;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {


	@Query(value = """
			SELECT book
			FROM libraries,
			     jsonb_array_elements(library->'books') AS book
			WHERE book->>'isbn' = :isbn
			""", nativeQuery = true)
	List<String> findBookByIsbn(@Param("isbn") String isbn);
}


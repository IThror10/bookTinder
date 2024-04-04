package com.binder.repository;

import com.binder.entity.Book;
import com.binder.response.BookResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("SELECT new com.binder.response.BookResponse(b.id, b.title, b.author, b.year, b.description) " +
        "FROM Book b WHERE lower(b.title) LIKE lower(concat('%', :name, '%'))")
    List<BookResponse> searchBooksLike(@Param("name") String name);

    Optional<Book> findByTitleAndAuthorAndDescriptionAndYear(String title, String author, String description, Integer year);
}

package com.binder.response;

import com.binder.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Информация о книге")
public class BookResponse {
    @Schema(description = "Id книги")
    private Long id;

    @Schema(description = "Название книги")
    private String title;

    @Schema(description = "Автор")
    private String author;

    @Schema(description = "Год")
    private Integer year;

    @Schema(description = "Описание книги")
    private String description;

    BookResponse(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        year = book.getYear();
        description = book.getDescription();
    }
}

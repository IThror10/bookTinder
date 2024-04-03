package com.binder.response;

import com.binder.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о книге")
public class BookResponse {
    @Schema(description = "Id книги")
    private Long id;

    @Schema(description = "Название книги")
    private String title;

    @Schema(description = "Автор")
    private String author;

    @Schema(description = "Издание")
    private String edition;

    @Schema(description = "Описание книги")
    private String bookDescription;

    BookResponse(Book book) {
        title = book.getTitle();
        author = book.getAuthor();
        edition = book.getEdition();
        bookDescription = book.getDescription();
    }
}
package com.binder.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация про книгу из запроса")
public record BookRequest (
    @Schema(description = "Id книги")
    Long id,

    @Schema(description = "Название книги")
    String title,

    @Schema(description = "Автор книги")
    String author,

    @Schema(description = "edition")
    String edition,

    @Schema(description = "Описание книги")
    String description
) {}

package com.binder.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация про книгу из запроса")
public record BookRequest (

    @Schema(description = "Id книги", example = "150")
    Long id,

    @Schema(description = "Название книги", example = "Some name")
    String title,

    @Schema(description = "Автор книги", example = "ABBA")
    String author,

    @Schema(description = "year", example = "2010")
    Integer year,

    @Schema(description = "Описание книги", example = "Some extended description about book")
    String description
) {}

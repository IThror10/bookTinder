package com.binder.request;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Запрос на создание нового объявления с предложением по обмену")
public record GiveAwayRequest(
    BookRequest book,

    @Schema(description = "Описание объявления")
    String description,

    @Schema(description = "Фотография книги из объявления")
    byte[] photo
) {}

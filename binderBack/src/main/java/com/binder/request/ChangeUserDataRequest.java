package com.binder.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Изменение данных пользователя")
public record ChangeUserDataRequest (

        @Schema(description = "Имя пользователя", example = "Jon")
        @NotBlank(message = "имя пользователя не может быть пустыми")
        String name,

        @Schema(description = "Персональная информация", example = "tg: IThror")
        String personal,

        @Schema(description = "Год рождения", example = "2010")
        Integer year,

        @Schema(description = "Фотография")
        byte[] photo
) {}
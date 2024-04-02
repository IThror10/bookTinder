package com.binder.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на регистрацию")
public record RegisterUserRequest(
        @Schema(description = "Login пользователя", example = "Jon")
        @NotBlank(message = "Login пользователя не может быть пустыми")
        String login,

        @Schema(description = "Имя пользователя", example = "Jon")
        @NotBlank(message = "имя пользователя не может быть пустыми")
        String name,

        @Schema(description = "Пароль", example = "my_1secret1_password")
        @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
        String password,

        @Schema(description = "Персональная информация", example = "tg: IThror")
        String personal,

        @Schema(description = "Год рождения", example = "2010")
        Integer year
) {}

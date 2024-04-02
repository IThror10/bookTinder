package com.binder.response;

import com.binder.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Информация о пользователе")
public class UserInfoResponse {
    @Schema(description = "Идентификатор пользователя", example = "0")
    Long userId;

    @Schema(description = "Год рождения", example = "2010")
    Integer year;
    @Schema(description = "Информация о пользователе", example = "Вы можете связаться со мной в tg @IThror")
    String personal;
    @Schema(description = "Имя пользователя", example = "8-2000")
    String name;

    public UserInfoResponse(User user) {
        userId = user.getId();
        year = 2000;
        personal = user.getContacts();
        name = user.getName();
    }
}

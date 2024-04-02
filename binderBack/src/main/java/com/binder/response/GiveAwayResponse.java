package com.binder.response;

import com.binder.entity.GiveAway;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "информация про объявление по обмену")
public class GiveAwayResponse {
    @Schema(description = "Id объявления")
    private Long id;

    @Schema(description = "Id пользователя")
    private Long userId;

    @Schema(description = "Описание объявления")
    private String description;

    @Schema(description = "Фотография")
    private byte[] photo;

    @Schema(description = "Книга")
    private BookResponse book;

    public GiveAwayResponse(GiveAway giveAway) {
        id = giveAway.getId();
        userId = giveAway.getUser().getId();
        description = giveAway.getDescription();
        photo = giveAway.getPhoto();
        book = new BookResponse(giveAway.getBook());
    }
}

package com.binder.response;

import com.binder.entity.GiveAway;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Заматченные книги")
public class MatchResponse {
    @Schema(implementation = GiveAwayResponse.class)
    GiveAway ours;

    @Schema(implementation = GiveAwayResponse.class)
    GiveAway other;
}

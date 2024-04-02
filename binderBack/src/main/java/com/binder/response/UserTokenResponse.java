package com.binder.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Ответ c токеном доступа")
public record UserTokenResponse (
        @Schema(implementation = UserInfoResponse.class)
        UserInfoResponse userData,

        @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
        String jsonAuth
) { }

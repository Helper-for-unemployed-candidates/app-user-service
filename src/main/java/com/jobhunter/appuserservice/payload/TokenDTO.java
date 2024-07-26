package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Authentication token")
public class TokenDTO {
    private Data data;
    private String message;
    @Schema(description = "Authentication token for the user", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String accessToken;
    @Schema(description = "Authentication token type", example = "Bearer ")
    private String tokenType = RestConstants.TOKEN_TYPE;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String token;
    }
}
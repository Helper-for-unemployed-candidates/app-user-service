package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Contains verification code's id. One of the fields always will be null.")
public class CodeDTO {
    @Schema(description = "ID of the sms code", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID smsCodeId;
    @Schema(description = "ID of the email code", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID emailCodeId;
}

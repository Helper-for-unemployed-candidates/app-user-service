package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendSmsDTO {
    @JsonProperty(value = "mobile_phone")
    private String mobilePhone;
    private String message;
    private String from;
    @JsonProperty(value = "callback_url")
    private String callbackUrl;
}

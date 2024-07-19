package com.jobhunter.appuserservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsResponseDTO {
    private UUID id;
    private String message;
    private String status;
}

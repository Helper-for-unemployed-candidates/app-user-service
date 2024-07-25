package com.jobhunter.appuserservice.payload;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {
    private UUID id;
    private String originalName;
    private long size;
    private String contentType;
    private String path;
}

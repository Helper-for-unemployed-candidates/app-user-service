package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.payload.AttachmentDTO;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.UUID;

@Tag(name = "Attachment Controller", description = "Attachment Management APIs")
@RequestMapping(value = AttachmentController.BASE_PATH)
public interface AttachmentController {

    String BASE_PATH = RestConstants.BASE_PATH_V1 + "/attachment";
    String UPLOAD_PATH = "/upload";

    @Operation(
            summary = "Upload a file",
            description = "Upload a file to the server",
            tags = {"Attachment Controller"}
    )
    @PostMapping(UPLOAD_PATH)
    Response<AttachmentDTO> uploadFile(@Parameter(description = "File to be uploaded", required = true) MultipartHttpServletRequest request);

    @Operation(
            summary = "Download a file by ID",
            description = "Download a file using its ID",
            tags = {"Attachment Controller"}
    )
    @GetMapping("/{id}")
    ResponseEntity<?> downloadFile(
            @Parameter(description = "ID of the file to be downloaded", required = true) @PathVariable UUID id,
            @Parameter(description = "View type: inline or attachment") @RequestParam(defaultValue = "inline") String view,
            HttpServletResponse response);
}

package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.payload.AttachmentDTO;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.UUID;

@RequestMapping(value = AttachmentController.BASE_PATH)
public interface AttachmentController {

    String BASE_PATH = RestConstants.BASE_PATH_V1 + "/attachment";
    String UPLOAD_PATH = "/upload";

    @PostMapping(UPLOAD_PATH)
    Response<AttachmentDTO> uploadFile(MultipartHttpServletRequest request);


    @GetMapping("/{id}")
    ResponseEntity<?> downloadFile(@PathVariable UUID id,
                                   @RequestParam(defaultValue = "inline") String view,
                                   HttpServletResponse response);
}

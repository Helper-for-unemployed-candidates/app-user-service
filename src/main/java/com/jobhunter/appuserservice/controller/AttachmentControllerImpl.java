package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.payload.AttachmentDTO;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.service.AttachmentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttachmentControllerImpl implements AttachmentController {
    private final AttachmentService attachmentService;


    @Override
    public Response<AttachmentDTO> uploadFile(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @Override
    public ResponseEntity<?> downloadFile(UUID id, String view, HttpServletResponse response) {
        return attachmentService.downloadFile(id, view, response);
    }
}

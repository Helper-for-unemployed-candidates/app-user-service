package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.Attachment;
import com.jobhunter.appuserservice.payload.AttachmentDTO;
import com.jobhunter.appuserservice.payload.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.UUID;

public interface AttachmentService {
    Attachment uploadAttachment(MultipartHttpServletRequest request);

    Response<AttachmentDTO> uploadFile(MultipartHttpServletRequest request);

    ResponseEntity<?> downloadFile(UUID id, String view, HttpServletResponse response);
}

package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.CodeDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    CodeDTO sendVerificationEmail(User user);

    CodeDTO passwordResetEmail(User user);

    void checkIfThereAreTooManyRequestsOrThrow(String email);
}

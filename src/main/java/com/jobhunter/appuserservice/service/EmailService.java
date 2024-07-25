package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.CodeDTO;
import com.jobhunter.appuserservice.payload.VerifyDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    CodeDTO sendVerificationEmail(User user, boolean check);

    void checkIfThereAreTooManyRequestsOrThrow(String email);

    void checkIfVerificationCodeIsValidOrThrow(VerifyDTO verifyDTO);

    CodeDTO sendUpdateEmail(String email, boolean check);
}

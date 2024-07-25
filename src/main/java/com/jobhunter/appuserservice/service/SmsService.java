package com.jobhunter.appuserservice.service;


import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.CodeDTO;
import com.jobhunter.appuserservice.payload.VerifyDTO;

public interface SmsService {
    CodeDTO sendVerificationSms(User user, boolean check);

    CodeDTO sendUpdatePhoneSms(String phoneNumber);

    void sendMessage(String phoneNumber, String message);

    void checkIfVerificationCodeIsValidOrThrow(VerifyDTO verifyDTO);

    void checkIfThereAreTooManyRequestsOrThrow(String phone);
}
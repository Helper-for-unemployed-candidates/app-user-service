package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.client.EskizClient;
import com.jobhunter.appuserservice.entities.SmsCode;
import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.exceptions.RestException;
import com.jobhunter.appuserservice.payload.SendSmsDTO;
import com.jobhunter.appuserservice.payload.CodeDTO;
import com.jobhunter.appuserservice.payload.VerifyDTO;
import com.jobhunter.appuserservice.repository.SmsCodeRepository;
import com.jobhunter.appuserservice.utils.CommonUtils;
import com.jobhunter.appuserservice.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class EskizSmsService implements SmsService {
    private final EskizClient eskizClient;
    private final TokenProviderService tokenProviderService;
    private final SmsCodeRepository smsCodeRepository;

    @Value("${app.utils.limit-sms-code}")
    private int limitSmsCode;
    @Value("${app.utils.limit-sms-code-duration-hour}")
    private int limitHour;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public CodeDTO sendVerificationSms(User user) {

//        checkIfThereAreTooManyRequestsOrThrow(user.getPhone());

        String verificationCode = CommonUtils.generateCode();

        String message = CommonUtils.generateMessageForSms(verificationCode);

        sendMessage(user.getPhone(), message);

        SmsCode smsCode = smsCodeRepository.save(new SmsCode(verificationCode, user.getPhone()));

        CodeDTO codeDTO = CodeDTO.builder().smsCodeId(smsCode.getId()).build();

        return codeDTO;
    }

    @Override
    public void sendMessage(String phoneNumber, String message) {
        log.info("'{}' message has been sent to phone number '{}'", message, phoneNumber);
        SendSmsDTO sms = SendSmsDTO.builder()
                .message(message)
                .mobilePhone(phoneNumber)
                .build();
        eskizClient.sendSms(sms, tokenProviderService.getToken());
    }

    @Override
    public void checkIfVerificationCodeIsValidOrThrow(VerifyDTO verifyDTO) {

    }

    @Override
    public void checkIfThereAreTooManyRequestsOrThrow(String phone) {
        if (!smsCodeRepository.nonOverLimit(limitSmsCode, limitHour, phone))
            throw RestException.restThrow(MessageConstants.MANY_SMS_HAS_BEEN_SENT, HttpStatus.TOO_MANY_REQUESTS);
    }
}

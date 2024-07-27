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

import java.util.Objects;


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
    @Value("${app.in-dev}")
    private boolean isDev;

    @Override
    public CodeDTO sendVerificationSms(User user, boolean check) {
        return sendMessageAndCheck(user.getPhone(), check);
    }

    @Override
    public CodeDTO sendUpdatePhoneSms(String phoneNumber) {
        return sendMessageAndCheck(phoneNumber, true);
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
        if (verifyDTO.getPhoneNumber() == null)
            throw RestException.restThrow(MessageConstants.PHONE_NUMBER_CAN_NOT_BE_EMPTY);

        SmsCode smsCode = smsCodeRepository.findFirstByPhoneNumberOrderByCreatedAtDesc(verifyDTO.getPhoneNumber())
                .orElseThrow(() -> RestException.restThrow(MessageConstants.SMS_HAS_NOT_BEEN_SENT_TO_THIS_NUMBER));

        if (smsCode.isChecked())
            throw RestException.restThrow(MessageConstants.VERIFICATION_CODE_ALREADY_USED);

        if (!Objects.equals(verifyDTO.getCodeId(), smsCode.getId()))
            throw RestException.restThrow(MessageConstants.VERIFICATION_CODE_EXPIRED);

        if (!Objects.equals(verifyDTO.getCode(), smsCode.getCode()))
            throw RestException.restThrow(MessageConstants.INVALID_VERIFICATION_CODE);

        smsCode.setChecked(true);
        smsCodeRepository.save(smsCode);
    }

    @Override
    public void checkIfThereAreTooManyRequestsOrThrow(String phone) {
        if (!smsCodeRepository.nonOverLimit(limitSmsCode, limitHour, phone))
            throw RestException.restThrow(MessageConstants.MANY_SMS_HAS_BEEN_SENT, HttpStatus.TOO_MANY_REQUESTS);
    }

    private CodeDTO sendMessageAndCheck(String phoneNumber, boolean check) {
        if (check)
            checkIfThereAreTooManyRequestsOrThrow(phoneNumber);

        String verificationCode = CommonUtils.generateCode(isDev);

        String message = CommonUtils.generateMessageForSms(verificationCode);

        sendMessage(phoneNumber, message);

        SmsCode smsCode = smsCodeRepository.save(new SmsCode(verificationCode, phoneNumber));

        return CodeDTO.builder().smsCodeId(smsCode.getId()).build();
    }
}

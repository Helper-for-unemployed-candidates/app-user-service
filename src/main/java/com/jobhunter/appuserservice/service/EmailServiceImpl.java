package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.EmailCode;
import com.jobhunter.appuserservice.entities.SmsCode;
import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.exceptions.RestException;
import com.jobhunter.appuserservice.payload.CodeDTO;
import com.jobhunter.appuserservice.payload.VerifyDTO;
import com.jobhunter.appuserservice.repository.EmailCodeRepository;
import com.jobhunter.appuserservice.utils.CommonUtils;
import com.jobhunter.appuserservice.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${app.mail-sender}")
    private String from;
    @Value("${app.utils.limit-email-code}")
    private int emailLimit;
    @Value("${app.utils.limit-email-code-duration-hour}")
    private int emailLimitHour;

    private final JavaMailSender javaMailSender;
    private final EmailCodeRepository emailCodeRepository;

    @Override
    public CodeDTO sendVerificationEmail(User user, boolean check) {
        return checkAndSend(user.getEmail(), "Verification Code", check);
    }

    public void sendMail(String to, String subject, String msg) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg);
            javaMailSender.send(message);
            /*MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(msg, true);
            javaMailSender.send(mimeMessage);*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void checkIfThereAreTooManyRequestsOrThrow(String email) {
        if (!emailCodeRepository.nonOverLimit(emailLimit, emailLimitHour, email))
            throw RestException.restThrow(MessageConstants.TOO_MANY_EMAIL_HAS_BEEN_SENT, HttpStatus.TOO_MANY_REQUESTS);
    }

    @Override
    public void checkIfVerificationCodeIsValidOrThrow(VerifyDTO verifyDTO) {
        if (verifyDTO.getEmail() == null)
            throw RestException.restThrow(MessageConstants.EMAIL_CAN_NOT_BE_EMPTY);

        EmailCode emailCode = emailCodeRepository.findFirstByEmailOrderByCreatedAtDesc(verifyDTO.getEmail())
                .orElseThrow(() -> RestException.restThrow(MessageConstants.MESSAGE_HAS_NOT_BEEN_SENT_TO_THIS_EMAIL));

        if (!Objects.equals(verifyDTO.getCodeId(), emailCode.getId()))
            throw RestException.restThrow(MessageConstants.VERIFICATION_CODE_EXPIRED);

        if (emailCode.isChecked())
            throw RestException.restThrow(MessageConstants.VERIFICATION_CODE_ALREADY_USED);

        if (!Objects.equals(verifyDTO.getCode(), emailCode.getCode()))
            throw RestException.restThrow(MessageConstants.INVALID_VERIFICATION_CODE);

        emailCode.setChecked(true);
        emailCodeRepository.save(emailCode);
    }

    @Override
    public CodeDTO sendUpdateEmail(String email, boolean check) {
        return checkAndSend(email, "Update Email", check);
    }

    private CodeDTO checkAndSend(String to, String subject, boolean check) {
        if (check)
            checkIfThereAreTooManyRequestsOrThrow(to);

        String verificationCode = CommonUtils.generateCode();

        String message = CommonUtils.generateMessageForEmail(verificationCode);

        sendMail(to, subject, message);

        EmailCode emailCode = emailCodeRepository.save(EmailCode.builder().email(to).code(verificationCode).build());

        return CodeDTO.builder().emailCodeId(emailCode.getId()).build();
    }
}

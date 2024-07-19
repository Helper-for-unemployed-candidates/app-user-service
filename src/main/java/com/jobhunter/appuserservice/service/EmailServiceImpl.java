package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.EmailCode;
import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.exceptions.RestException;
import com.jobhunter.appuserservice.payload.CodeDTO;
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
    public CodeDTO sendVerificationEmail(User user) {

//        checkIfThereAreTooManyRequestsOrThrow(user.getEmail());

        String verificationCode = CommonUtils.generateCode();

        String message = CommonUtils.generateMessageForEmail(verificationCode);

        sendMail(user.getEmail(), "Registration", message);

        EmailCode emailCode = emailCodeRepository.save(EmailCode.builder().email(user.getEmail()).code(verificationCode).build());

        CodeDTO codeDTO = CodeDTO.builder().emailCodeId(emailCode.getId()).build();

        return codeDTO;
    }

    @Override
    public CodeDTO passwordResetEmail(User user) {
        sendMail(user.getEmail(), "Reset Password", CommonUtils.generateCode());
        return null;
    }

    public void sendMail(String to, String subject, String msg) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg);
            message.setCc();
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
}

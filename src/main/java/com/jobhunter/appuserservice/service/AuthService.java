package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.repository.projection.SphereProjection;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface AuthService extends UserDetailsService {
    User getUserById(UUID id);

    Response<CodeDTO> signUp(SignUpDTO signUpDTO);

    Response<TokenDTO> signIn(SignInDTO signInDTO);

    Response<CodeDTO> resendVerificationCode(String username);

    Response<String> resetPassword(ResetPasswordDTO resetPasswordDTO);

    /**
     * @param username User's phone number or email.
     */
    Response<CodeDTO> forgotPassword(String username);

    Response<String> verifyAccount(VerifyDTO verifyDTO);

    Response<List<SphereProjection>> initialData();
}

package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.repository.projection.SphereProjection;
import com.jobhunter.appuserservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public Response<CodeDTO> signUp(SignUpDTO signUpDTO) {
        return authService.signUp(signUpDTO);
    }

    @Override
    public Response<TokenDTO> signIn(SignInDTO signInDTO) {
        return authService.signIn(signInDTO);
    }

    @Override
    public Response<CodeDTO> resendVerificationCode(String username) {
        return authService.resendVerificationCode(username);
    }

    @Override
    public Response<CodeDTO> forgotPassword(String username) {
        return authService.forgotPassword(username);
    }

    @Override
    public Response<String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        return authService.resetPassword(resetPasswordDTO);
    }

    @Override
    public Response<String> verifyAccount(VerifyDTO verifyDTO) {
        return authService.verifyAccount(verifyDTO);
    }

    @Override
    public Response<List<SphereProjection>> initialData() {
        return authService.initialData();
    }
}

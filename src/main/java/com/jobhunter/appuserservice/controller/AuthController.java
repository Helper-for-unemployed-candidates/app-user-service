package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.repository.projection.SphereProjection;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = AuthController.BASE_PATH)
public interface AuthController {

    String BASE_PATH = RestConstants.BASE_PATH_V1 + "/auth";
    String SIGN_UP_PATH = "/sign-up";
    String SIGN_IN_PATH = "/sign-in";
    String RESEND_FOR_SIGN_IN_PATH = "/resend-sign-in";
    String FORGOT_PASSWORD_PATH = "/forgot-password";
    String RESET_PASSWORD_PATH = "/reset-password";
    String VERIFY_ACCOUNT_PATH = "/verify-account";
    String INITIAL_DATA_PATH = "/initial-data";

    @PostMapping(SIGN_UP_PATH)
    Response<CodeDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    @PostMapping(SIGN_IN_PATH)
    Response<TokenDTO> signIn(@Valid @RequestBody SignInDTO signInDTO);

    @GetMapping(RESEND_FOR_SIGN_IN_PATH)
    Response<CodeDTO> resendVerificationCode(@RequestParam String username);

    @GetMapping(FORGOT_PASSWORD_PATH)
    Response<String> forgotPassword(@RequestParam String username);

    @PutMapping(RESET_PASSWORD_PATH)
    Response<String> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);

    @PostMapping(VERIFY_ACCOUNT_PATH)
    Response<String> verifyAccount(@Valid @PathVariable VerifyDTO verifyDTO);

    @GetMapping(INITIAL_DATA_PATH)
    Response<List<SphereProjection>> initialData();
}

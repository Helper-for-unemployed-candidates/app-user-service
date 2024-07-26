package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.repository.projection.SphereProjection;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auth API", description = "API Description for authentication")
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

    @Operation(
            summary = "Sign Up",
            description = "Register a new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sign up DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignUpDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful registration",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CodeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping(SIGN_UP_PATH)
    Response<CodeDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO);

    @Operation(
            summary = "Sign In",
            description = "Authenticate a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sign In DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignInDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TokenDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping(SIGN_IN_PATH)
    Response<TokenDTO> signIn(@Valid @RequestBody SignInDTO signInDTO);

    @Operation(
            summary = "Resend Verification Code.",
            description = "Resend the verification code for sign-in. Works if user hasn't registered yet.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CodeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @GetMapping(RESEND_FOR_SIGN_IN_PATH)
    Response<CodeDTO> resendVerificationCode(@Parameter(description = "User's phone number or email.", required = true) @RequestParam String username);

    @Operation(
            summary = "Forgot Password.",
            description = "Request a code to reset password. Works if user has registered already.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CodeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @GetMapping(FORGOT_PASSWORD_PATH)
    Response<CodeDTO> forgotPassword(@Parameter(description = "User's phone number or email.") @RequestParam String username);

    @Operation(
            summary = "Reset Password",
            description = "Reset the user's password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Reset Password DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResetPasswordDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password successfully reset",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PutMapping(RESET_PASSWORD_PATH)
    Response<String> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO);

    @Operation(
            summary = "Verify Account",
            description = "Verify a user's account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verify DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VerifyDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account successfully verified",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping(VERIFY_ACCOUNT_PATH)
    Response<String> verifyAccount(@Valid @RequestBody VerifyDTO verifyDTO);

    @Operation(
            summary = "Initial Data",
            description = "Get initial data for creating a company. Gets sphere's list.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SphereProjection.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @GetMapping(INITIAL_DATA_PATH)
    Response<List<SphereProjection>> initialData();
}

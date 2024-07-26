package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.security.CurrentUser;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.UUID;

@RequestMapping(UserController.BASE_PATH)
public interface UserController {
    String BASE_PATH = RestConstants.BASE_PATH_V1 + "/account";
    String BLOCK = "/block";
    String UNBLOCK = "/unblock";
    String LIST = "/list";
    String EMAIL_UPDATE = "/email";
    String VERIFY_EMAIL = "/verify-email";
    String PHONE_NUMBER_UPDATE = "/phone";
    String VERIFY_PHONE_NUMBER = "/verify-phone";
    String PASSWORD_UPDATE = "/password";
    String ADDRESS_UPDATE = "/address";
    String AVATAR_UPDATE = "/avatar";

    @Operation(
            summary = "Get Current User",
            description = "Retrieve information about the currently authenticated user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserWithoutPasswordDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access")
            }
    )
    @GetMapping
    Response<UserWithoutPasswordDTO> getMe(@CurrentUser User user);

    @Operation(
            summary = "Block User",
            description = "Block a user by their ID",
            parameters = @Parameter(
                    name = "userId",
                    description = "ID of the user to block",
                    example = "123e4567-e89b-12d3-a456-426614174000"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully blocked",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden access")
            }
    )
    @PutMapping(BLOCK + "/{userId}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    Response<String> blockUser(@PathVariable UUID userId);

    @Operation(
            summary = "Unblock User",
            description = "Unblock a user by their ID",
            parameters = @Parameter(
                    name = "userId",
                    description = "ID of the user to unblock",
                    example = "123e4567-e89b-12d3-a456-426614174000"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully unblocked",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden access")
            }
    )
    @PutMapping(UNBLOCK + "/{userId}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    Response<String> unblockUser(@PathVariable UUID userId);

    @Operation(
            summary = "List Users",
            description = "Retrieve a paginated list of users",
            parameters = {
                    @Parameter(name = "userRole", description = "Role of the users to list(APPLICANT or COMPANY)", example = "APPLICANT"),
                    @Parameter(name = "asc", description = "Sort order. if ascending - true, if not then - false", example = "false"),
                    @Parameter(name = "sortType", description = "Field to sort by", example = "createdAt"),
                    @Parameter(name = "search", description = "Search query", example = "John Doe"),
                    @Parameter(name = "page", description = "Page number", example = "1"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user applicant list",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicantProjectionDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user company list",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CompanyProjectionDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden access")
            }
    )
    @GetMapping(LIST)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    Response<PaginationDTO<?>> list(@RequestParam(defaultValue = RestConstants.APPLICANT) String userRole,
                                    @RequestParam(required = false) boolean asc,
                                    @RequestParam(defaultValue = "createdAt") String sortType,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);

    @Operation(
            summary = "Update User",
            description = "Update the details of the currently authenticated user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User Update DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserUpdateDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserWithoutPasswordDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PutMapping
    Response<UserWithoutPasswordDTO> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @CurrentUser User currentUser);

    @Operation(
            summary = "Update user address",
            description = "Updates the address of the current user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressUpdateDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Address updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PutMapping(ADDRESS_UPDATE)
    Response<String> updateAddress(@CurrentUser User user, @Valid @RequestBody AddressUpdateDTO address);

    @Operation(
            summary = "Get list of regions",
            description = "Retrieves a list of regions with associated cities.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Regions retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDTO.class)
                            )
                    )
            }
    )
    @GetMapping(ADDRESS_UPDATE)
    Response<List<RegionDTO>> getRegions();

    @Operation(
            summary = "Update user avatar",
            description = "Updates the avatar of the current user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Multipart file for user avatar",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data"
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avatar updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PutMapping(AVATAR_UPDATE)
    Response<String> updateAvatar(@CurrentUser User currentUser, MultipartHttpServletRequest request);

    @Operation(
            summary = "Update user email",
            description = "Updates the email of the current user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmailUpdateDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Email updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CodeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PutMapping(EMAIL_UPDATE)
    Response<CodeDTO> updateEmail(@CurrentUser User currentUser, @Valid @RequestBody EmailUpdateDTO email);

    @Operation(
            summary = "Verify Email",
            description = "Verify the email address of the currently authenticated user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verification DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VerifyDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Email successfully verified",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PutMapping(VERIFY_EMAIL)
    Response<String> verifyEmail(@CurrentUser User currentUser, @Valid @RequestBody VerifyDTO verifyDTO);

    @Operation(
            summary = "Update Phone Number",
            description = "Update the phone number of the currently authenticated user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Phone Update DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PhoneUpdateDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Phone number successfully updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CodeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PutMapping(PHONE_NUMBER_UPDATE)
    Response<CodeDTO> updatePhone(@CurrentUser User currentUser, @Valid @RequestBody PhoneUpdateDTO phone);

    @Operation(
            summary = "Verify Phone Number",
            description = "Verify the phone number of the currently authenticated user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verification DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VerifyDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Phone number successfully verified",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PutMapping(VERIFY_PHONE_NUMBER)
    Response<String> verifyPhoneNumber(@CurrentUser User currentUser, @Valid @RequestBody VerifyDTO verifyDTO);

    @Operation(
            summary = "Update user password",
            description = "Update the password of the currently authenticated user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update password DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePasswordDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    @PutMapping(PASSWORD_UPDATE)
    Response<String> updatePassword(@CurrentUser User currentUser, @Valid @RequestBody UpdatePasswordDTO passwordDTO);

    @Operation(
            summary = "Delete user account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User account deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            }
    )
    @DeleteMapping
    @PreAuthorize(value = "hasAnyAuthority('APPLICANT', 'COMPANY')")
    Response<String> deleteUser(@CurrentUser User currentUser);
}

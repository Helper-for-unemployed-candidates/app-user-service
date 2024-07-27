package com.jobhunter.appuserservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhunter.appuserservice.controller.AuthController;

public interface RestConstants {

    String[] OPEN_PAGES = {
            "/actuator/health",
            AuthController.BASE_PATH + AuthController.SIGN_IN_PATH,
            AuthController.BASE_PATH + "/**",
            RestConstants.BASE_PATH_V1 + "/api-docs/**",
            RestConstants.BASE_PATH_V1 + "/swagger-ui/**",
            RestConstants.BASE_PATH_V1 + "/swagger-ui.html"
    };

    String BASE_PATH = "/api";
    String BASE_PATH_V1 = BASE_PATH + "/v1/u";

    String AUTHENTICATION_HEADER = "Authorization";
    ObjectMapper objectMapper = new ObjectMapper();

    String TOKEN_TYPE = "Bearer ";


    int INCORRECT_USERNAME_OR_PASSWORD = 3001;

    int REQUIRED = 3007;

    int SERVER_ERROR = 3008;
    int CONFLICT = 3009;
    int NO_ITEMS_FOUND = 3011;
    int CONFIRMATION = 3012;
    int USER_NOT_ACTIVE = 3013;
    int JWT_TOKEN_INVALID = 3014;
    String VERIFICATION_EMAIL_BODY = "src/main/resources/html/VerificationEmail.html";
    String PASSWORD_RESET_EMAIL_BODY = "src/main/resources/html/PasswordResetEmail.html";
    String VERIFICATION_EMAIL_FOR_REGISTERED_USER = "src/main/resources/html/VerificationEmailForRegisteredUser.html";
    String FROM_EMAIL = "sirojiddinit@gmail.com";
    String SUPPORT_EMAIL_ADDRESS = "support@gmail.com";
    String APPLICATION_NAME = "WNL";
    String VERIFICATION_EMAIL_SUBJECT = "Verify Your Email";
    String PASSWORD_RESET_EMAIL_SUBJECT = "Password Reset Request";
    String UPDATE_YOUR_EMAIL_SUBJECT = "Update your email";
    String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?£$%^&\\-+*=/\\\\_])[A-Za-z\\d@!?£$%^&\\-+*=/\\\\_]{8,}$";
    String USA_PHONE_NUMBER_REGEX = """
            ^(\\+1)?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$""";
    String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    String NUMBER_REGEX = "^\\d+$";
    int nameCol = 0;
    int addressCol = 1;
    int cityCol = 2;
    int stateCol = 3;
    int zipCol = 4;
    int mcCol = 5;
    int decisionCol = 6;
    int idCol = 7;
    int startRowIndex = 2;
    int additionRows = 3;
    int companyRowSize = 8;
    String NUMBER_GREATER = "NUMBER_GREATER";
    String ONE_OF_LIST = "ONE_OF_LIST";
    String USER_ENTERED = "USER_ENTERED";

    String DEFAULT_PAGE = "0";
    String DEFAULT_PAGE_SIZE_FOR_SEARCH = "5";
    String DEFAULT_PAGE_SIZE = "10";
    String LOWER_CASE = "[a-z]";
    String UPPER_CASE = "[A-Z]";
    String CONTAINS_NUMBERS = "[\\d]";
    String SPECIAL_SYMBOLS = "[@!?£$%^&\\-+*=/\\\\_]";
    String MIN_LENGTH = ".{8}";
    String MAX_LENGTH = "^.{8,30}$";
    String UZB_CODE = "+998";
    String UZB_PHONE_NUMBER_REGEX = "^(33|55|88|90|91|93|94|95|97|99)\\d{7}$";
    String APPLICANT = "applicant";
    String COMPANY = "company";
}

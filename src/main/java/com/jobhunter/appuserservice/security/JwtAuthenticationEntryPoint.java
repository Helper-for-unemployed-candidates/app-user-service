package com.jobhunter.appuserservice.security;

import com.jobhunter.appuserservice.payload.ErrorData;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        log.error("Responding with unauthorized error. URL -  {}, Message - {}", httpServletRequest.getRequestURI(), e.getMessage());
        Response<ErrorData> errorDataResponse = Response.errorResponse("Unauthorized access", HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(RestConstants.objectMapper.writeValueAsString(errorDataResponse));
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

}

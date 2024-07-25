package com.jobhunter.appuserservice.utils;

import com.jobhunter.appuserservice.exceptions.RestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Random;

public interface CommonUtils {
    Random random = new Random();

    static String generateCode() {
        return String.valueOf(random.nextInt(100000, 999999));
    }

    static String generateMessageForSms(String code) {
        return code;
    }

    static String generateMessageForEmail(String code) {
        return code;
    }

    static HttpServletRequest currentRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        return servletRequestAttributes.getRequest();
    }

    static void checkPageAndSize(int page, int size) {
        if (page < 0 || size < 1)
            throw RestException.restThrow(MessageConstants.PAGE_OR_SIZE_ERROR);
    }
}

package com.jobhunter.appuserservice.utils;

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
}

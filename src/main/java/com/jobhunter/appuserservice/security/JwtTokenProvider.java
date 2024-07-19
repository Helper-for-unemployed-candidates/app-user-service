package com.jobhunter.appuserservice.security;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.exceptions.RestException;
import com.jobhunter.appuserservice.utils.MessageConstants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.access.key}")
    private String JWT_SECRET_KEY_FOR_ACCESS_TOKEN;
    @Value("${jwt.access.expiration-time}")
    private Long JWT_EXPIRED_TIME_FOR_ACCESS_TOKEN;
    @Value("${jwt.refresh.key}")
    private String JWT_SECRET_KEY_FOR_REFRESH_TOKEN;
    @Value("${jwt.refresh.expiration-time}")
    private Long JWT_EXPIRED_TIME_FOR_REFRESH_TOKEN;
    @Value("${jwt.update-email.key}")
    private String JWT_SECRET_KEY_FRO_EMAIL_UPDATE_TOKEN;
    @Value("${jwt.update-email.expiration-time}")
    private Long JWT_EXPIRED_TIME_FOR_EMAIL_UPDATE_TOKEN;

    public String generateAccessToken(User user, Timestamp issuedAt) {
        Timestamp expireDate = new Timestamp(System.currentTimeMillis() + JWT_EXPIRED_TIME_FOR_ACCESS_TOKEN);
        String userId = String.valueOf(user.getId());
        String generateUuid = hideUserId(userId);
        return Jwts.builder()
                .setSubject(generateUuid)
                .setIssuedAt(issuedAt)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY_FOR_ACCESS_TOKEN)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());
        Timestamp expireDate = new Timestamp(System.currentTimeMillis() + JWT_EXPIRED_TIME_FOR_REFRESH_TOKEN);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY_FOR_REFRESH_TOKEN)
                .compact();
    }

    public boolean validToken(String token, boolean accessToken) {
        try {
            checkToken(token, accessToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Timestamp getIssuedAt(String token) {
        Jws<Claims> claimsFromToken = getClaimsFromToken(token, JWT_SECRET_KEY_FOR_ACCESS_TOKEN);
        Date issuedAt = claimsFromToken.getBody().getIssuedAt();
        return new Timestamp(issuedAt.getTime());
    }

    public void checkToken(String token, boolean accessToken) {
        getClaimsFromToken(token, accessToken ?
                JWT_SECRET_KEY_FOR_ACCESS_TOKEN : JWT_SECRET_KEY_FOR_REFRESH_TOKEN);
    }

    private Jws<Claims> getClaimsFromToken(String token, String key) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
    }

    public String getUserIdFromToken(String token, boolean accessToken) {
        String userId = Jwts.parser()
                .setSigningKey(accessToken ? JWT_SECRET_KEY_FOR_ACCESS_TOKEN : JWT_SECRET_KEY_FOR_REFRESH_TOKEN)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return showUserId(userId);

    }

    public String generateEmailUpdateVerificationToken(String email, String verificationCode) {
        return Jwts.builder()
                .setSubject(verificationCode)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED_TIME_FOR_EMAIL_UPDATE_TOKEN))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY_FRO_EMAIL_UPDATE_TOKEN)
                .compact();
    }

    public Map<String, String> getEmailFromVerificationToken(String token) {
        try {
            Claims body = getClaimsFromToken(token, JWT_SECRET_KEY_FRO_EMAIL_UPDATE_TOKEN).getBody();
            final String verificationCode = body.getSubject();
            String email = "";
            if (body.get("email") instanceof String em) {
                email = em;
            }
            String finalEmail = email;
            return new HashMap<>() {{
                put("verificationCode", verificationCode);
                put("email", finalEmail);
            }};
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RestException(MessageConstants.VERIFICATION_TOKEN_EXPIRED, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            throw new RestException(MessageConstants.VERIFICATION_TOKEN_INVALID, HttpStatus.BAD_REQUEST);
        }
    }

    private String hideUserId(String userId) {
        String generatingUUID = String.valueOf(UUID.randomUUID());
        String substring = generatingUUID.substring(0, 18);
        String concat = substring.concat("-");
        String concat1 = concat.concat(userId);
        String substring1 = generatingUUID.substring(18);
        return concat1.concat(substring1);
    }

    private String showUserId(String concat) {
        return concat.substring(19, 43);
    }
}

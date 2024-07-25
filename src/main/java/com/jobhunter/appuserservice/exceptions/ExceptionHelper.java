package com.jobhunter.appuserservice.exceptions;

import com.jobhunter.appuserservice.payload.ErrorData;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionHelper {


    // FOYDALANUVCHI TOMONIDAN XATO SODIR BO'LGANDA
    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<Response<ErrorData>> handleException(RestException ex) {
        ex.printStackTrace();
        //AKS HOLDA DOIMIY EXCEPTIONLAR ISHLAYVERADI
        if (ex.getFieldName() == null && ex.getErrors() != null) {
            return new ResponseEntity<>(Response.errorResponse(ex.getErrors()), ex.getStatus());
        }
        //AGAR RESOURSE TOPILMAGANLIGI XATOSI BO'LSA CLIENTGA QAYSI TABLEDA NIMA TOPILMAGANLIGI HAQIDA XABAR QAYTADI
        return new ResponseEntity<>(Response.errorResponse(ex.getUserMsg(), ex.getStatus().value()), ex.getStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        List<ErrorData> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            /*String[] codes = error.getCodes();
            assert codes != null;
            String code = codes[codes.length - 1];*/
            String errorMessage = error.getDefaultMessage();
            FieldError fieldError = (FieldError) error;
            if (Objects.equals(errorMessage, MessageConstants.PASSWORD_SHOULD_MATCH_REGEX))
                errorMessage = passwordError(fieldError);
            errors.add(new ErrorData(errorMessage, HttpStatus.BAD_REQUEST.value(), fieldError.getField()));
        });
        return new ResponseEntity<>(Response.errorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    private String passwordError(FieldError fieldError) {
        if (fieldError.getRejectedValue() == null) {
            return MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY;
        }
        String password = fieldError.getRejectedValue().toString();
        if (!Pattern.compile(RestConstants.LOWER_CASE).matcher(password).find()) {
            return MessageConstants.PASSWORD_SHOULD_CONTAIN_LOWER_CASE;
        } else if (!Pattern.compile(RestConstants.UPPER_CASE).matcher(password).find()) {
            return MessageConstants.PASSWORD_SHOULD_CONTAIN_UPPER_CASE;
        } else if (!Pattern.compile(RestConstants.CONTAINS_NUMBERS).matcher(password).find()) {
            return MessageConstants.PASSWORD_SHOULD_CONTAIN_NUMBERS;
        } else if (!Pattern.compile(RestConstants.SPECIAL_SYMBOLS).matcher(password).find()) {
            return MessageConstants.PASSWORD_SHOULD_CONTAIN_SPECIAL_SYMBOL;
        } else if (!Pattern.compile(RestConstants.MIN_LENGTH).matcher(password).find()) {
            return MessageConstants.PASSWORD_SHOULD_CONTAIN_MIN_8_CHARACTERS;
        } else if (!Pattern.compile(RestConstants.MAX_LENGTH).matcher(password).find()) {
            return MessageConstants.PASSWORD_SHOULD_CONTAIN_MAX_30_CHARACTERS;
        }
        return MessageConstants.PASSWORD_SHOULD_MATCH_REGEX;
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<?> handleException(TypeMismatchException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleException(MissingServletRequestParameterException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<?> handleException(ServletRequestBindingException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestPartException.class})
    public ResponseEntity<?> handleException(MissingServletRequestPartException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<?> handleException(BindException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleException(AccessDeniedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.DO_NOT_HAVE_PERMISSION_TO_USE_THIS_WAY, 403), //"Bu yo'lga kirishga huquq yo'q"
                HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<?> handleException(MissingPathVariableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.PAGE_NOT_FOUND, 404), //ex.me
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleException(NoHandlerFoundException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }


    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.METHOD_ERROR, 405),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotAcceptableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.UNACCEPTABLE, 406),
                HttpStatus.NOT_ACCEPTABLE);
    }


    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.METHOD_ERROR, 415),
                HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(value = {ConversionNotSupportedException.class})
    public ResponseEntity<?> handleException(ConversionNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {HttpMessageNotWritableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotWritableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*@ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> handleException(UsernameNotFoundException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(
                        ex.getMessage(), 400
                ), HttpStatus.BAD_REQUEST
        );
    }*/


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.SOMETHING_WENT_WRONG, 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public ResponseEntity<?> handleException(AsyncRequestTimeoutException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 503),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = {StringIndexOutOfBoundsException.class})
    public ResponseEntity<?> handleException(StringIndexOutOfBoundsException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<?> handleException(AuthenticationException ex) {
        return new ResponseEntity<>(
                Response.errorResponse("Unauthorized access", HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED
        );
    }


    // FOYDALANUVCHI TOMONIDAN XATO SODIR BO'LGANDA
//    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
//    public ResponseEntity<ApiResult<List<ErrorData>>> handleException(EmptyResultDataAccessException ex) {
//        ex.printStackTrace();
//        //AGAR RESOURSE TOPILMAGANLIGI XATOSI BO'LSA CLIENTGA QAYSI TABLEDA NIMA TOPILMAGANLIGI HAQIDA XABAR QAYTADI
//        return new ResponseEntity(ApiResult.errorResponse(MessagesEnum.getMessage("THIS_OBJECT_HAS_NOT"), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
//    }

}

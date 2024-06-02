package com.itr.challenge.error;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class, IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final Exception e) {

        log.error("----- Exception " + e.getClass() + " caused BAD_REQUEST status" + getStackTrace(e));

        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final UserNotFoundException e) {

        log.error("----- Error " + e.getClass() + " caused NOT_FOUND status" + getStackTrace(e));

        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            UserExistsException.class,
            DataIntegrityViolationException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflicts(final Exception e) {

        log.error("----- Error " + e.getClass() + " caused CONFLICT status");

        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            TokenExpiredException.class, SignatureVerificationException.class,
            JWTDecodeException.class, JWTVerificationException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleJwt(final Exception e) {

        log.error("----- Error " + e.getClass() + " caused UNAUTHORIZED status");

        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccess(final Exception e) {

        log.error("----- Error " + e.getClass() + " caused FORBIDDEN status");

        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(final Throwable e) {

        log.error("----- Error " + e.getClass() + " caused INTERNAL_SERVER_ERROR status" + getStackTrace(e));

        return new ErrorResponse(e.getMessage());
    }


    private String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

}
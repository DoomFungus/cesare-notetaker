package edu.kpi.notetaker.exceptionhandling;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityAlreadyExistsException;
import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFoundException;
import edu.kpi.notetaker.exceptionhandling.exceptions.TokenExpiredException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorMessage(LocalDateTime.now(), HttpStatus.CONFLICT, ex.getMessage(),
                        ((ServletWebRequest)request).getRequest().getRequestURI()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(),
                        ((ServletWebRequest)request).getRequest().getRequestURI()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {TokenExpiredException.class})
    protected ResponseEntity<Object> handleUnauthorised(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ErrorMessage(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, ex.getMessage(),
                        ((ServletWebRequest)request).getRequest().getRequestURI()),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}

package edu.example.hw1.api.exceptionHandlers;

import edu.example.hw1.api.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return handleException(entityNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> handleException(String exceptionMessage, HttpStatusCode status) {
        Map<String, Object> body = new HashMap<>();

        body.put("error", exceptionMessage);
        return new ResponseEntity<>(body, status);
    }
}

package org.xjudge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ScrappingExceptionHandler {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    @ExceptionHandler(ScrappingException.class)
    public ResponseEntity<ExceptionModel> ScrapException(ScrappingException exception) {
        ExceptionModel model = new ExceptionModel(exception.getStatusCode().value(),
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                exception.getStatusCode().getReasonPhrase());
        return new ResponseEntity<>(model, exception.getStatusCode());
    }
}

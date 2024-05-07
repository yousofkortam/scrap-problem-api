package org.xjudge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScrappingException extends RuntimeException {
    private final HttpStatus statusCode;

    public ScrappingException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}

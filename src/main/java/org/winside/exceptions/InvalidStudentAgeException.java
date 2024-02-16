package org.winside.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidStudentAgeException extends ResponseStatusException {
    public InvalidStudentAgeException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}

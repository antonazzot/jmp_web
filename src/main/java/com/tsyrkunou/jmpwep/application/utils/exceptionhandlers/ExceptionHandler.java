package com.tsyrkunou.jmpwep.application.utils.exceptionhandlers;

import static com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.ExceptionChainUtils.getMessageChain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.tsyrkunou.jmpwep.application.utils.NotFoundException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> catchException(MyAppException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageChain(e));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> catchException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageChain(e));
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> catchException(InsufficientRepositoryRightsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageChain(e));
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> catchException(IntegrationUnexpectedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageChain(e));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> catchException(RepositoryNotFoundOrInaccessibleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageChain(e));
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> catchException(SingleValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageChain(e));
    }
}

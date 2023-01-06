package com.tsyrkunou.jmpwep.application.security.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsyrkunou.jmpwep.application.security.service.AuthenticationService;
import com.tsyrkunou.jmpwep.application.security.web.model.AuthenticationRequest;
import com.tsyrkunou.jmpwep.application.security.web.model.AuthenticationResponse;
import com.tsyrkunou.jmpwep.application.security.web.model.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        return ResponseEntity.ok(service.authenticate(request, req, resp));
    }
}


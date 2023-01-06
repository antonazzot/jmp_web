package com.tsyrkunou.jmpwep.application.security.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.repository.CustomerRepository;
import com.tsyrkunou.jmpwep.application.security.jwt.JwtService;
import com.tsyrkunou.jmpwep.application.security.jwt.UserTokenRepository;
import com.tsyrkunou.jmpwep.application.security.model.Authority;
import com.tsyrkunou.jmpwep.application.security.model.UserPrincipal;
import com.tsyrkunou.jmpwep.application.security.model.UserToken;
import com.tsyrkunou.jmpwep.application.security.web.model.AuthenticationRequest;
import com.tsyrkunou.jmpwep.application.security.web.model.AuthenticationResponse;
import com.tsyrkunou.jmpwep.application.security.web.model.RegisterRequest;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class AuthenticationService {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserTokenRepository userTokenRepository;
    private final AuthenticationManager authenticationManager;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public AuthenticationService(CustomerRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
                                 UserTokenRepository userTokenRepository, AuthenticationManager authenticationManager,
                                 MeterRegistry meterRegistry) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userTokenRepository = userTokenRepository;
        this.authenticationManager = authenticationManager;
        meterRegistry.gauge("MyMetric", atomicInteger);
    }

    @Transactional
    @Timed
    public AuthenticationResponse register(RegisterRequest request) {
        Map<String, Object> claimsMap = Map.of("FirstName", request.getFirstname(),
                "LastName", request.getLastname(),
                "email", request.getEmail(),
                "password", passwordEncoder.encode(request.getPassword()));

        String token = jwtService.generateToken(claimsMap, request.getEmail());

        UserToken savedToken = userTokenRepository.save(UserToken.builder().token(token).build());

        Customer customer = Customer.builder()
                .name(request.getFirstname())
                .email(request.getEmail())
                .authorities(List.of(Authority.USER))
                .userToken(savedToken)
                .build();

        Customer savedCustomer = repository.save(customer);

        UserPrincipal userPrincipal = new UserPrincipal(savedCustomer);

        String jwt = jwtService.generateToken(claimsMap, userPrincipal);

        savedToken.setToken(jwt);
        userTokenRepository.save(savedToken);

        atomicInteger.set(Math.toIntExact(userPrincipal.getUser().getId()));
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        Map<String, Object> claimsMap = Map.of("FirstName", request.getFirstname(),
                "LastName", request.getLastname(),
                "email", request.getEmail(),
                "password", passwordEncoder.encode(request.getPassword()));

        String token = jwtService.generateToken(claimsMap, request.getEmail());

        UserToken savedToken = userTokenRepository.save(UserToken.builder().token(token).build());

        Customer customer = Customer.builder()
                .name(request.getFirstname())
                .email(request.getEmail())
                .authorities(List.of(Authority.ADMINISTRATOR))
                .userToken(savedToken)
                .build();

        Customer savedCustomer = repository.save(customer);

        UserPrincipal userPrincipal = new UserPrincipal(savedCustomer);

        String jwt = jwtService.generateToken(claimsMap, userPrincipal);

        savedToken.setToken(jwt);
        userTokenRepository.save(savedToken);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletRequest req,
                                               HttpServletResponse resp)
            throws IOException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        passwordEncoder.encode(request.getPs())
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(new UserPrincipal(user));
        resp.addHeader("Authorization", jwtToken);
        resp.sendRedirect("http://localhost:8080/jmp/hello");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
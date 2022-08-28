package com.tsyrkunou.jmpwep.application.feign;

import java.math.BigDecimal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tsyrkunou.jmpwep.application.converter.ApplicationConverter;
import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
import com.tsyrkunou.jmpwep.application.model.customer.Customer;
import com.tsyrkunou.jmpwep.application.model.customer.CustomerData;
import com.tsyrkunou.jmpwep.application.model.github.GithubRepository;
import com.tsyrkunou.jmpwep.application.model.github.GithubUser;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/feign")
@RestController
public class GitFeginClientController {
    private final GithubFeignClient githubFeignClient;
    private final LocalFeignClient localFeignClient;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final CustomerService customerService;
    private final ApplicationConverter applicationConverter;

    @GetMapping("/fork")
    public GithubRepository refork(@Value("${token}") String token, @Value("${owner}") String owner,
                                   @Value("${repo}") String repo) {
        githubFeignClient.getUser("Bearer " + token);
        GithubRepository githubRepository = githubFeignClient.forkRepository("Bearer " + token, owner, repo);
        String gitName = githubRepository.getGitName();
        log.info("GitName={}", gitName);
        return githubRepository;
    }

    @GetMapping("/user")
    @SneakyThrows
    public GithubUser getSomething(@Value("${token}") String token, HttpServletRequest request,
                                   HttpServletResponse response) {
        GithubUser user = githubFeignClient.getUser("Bearer " + token);
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .name(user.getUsername())
                .balance(BigDecimal.valueOf(100))
                .build();
        CustomerData customerData = applicationConverter.convert(createCustomerRequest);
        Gson g = new Gson();
        String s = g.toJson(createCustomerRequest);
        response.getWriter().write(s);
//        response.flushBuffer();
        request.getRequestDispatcher("http://localhost:8089/jmp").forward(request, response);
        response.sendRedirect("http://localhost:8089/jmp");
        Customer customer = customerService.createCustomer(customerData);

//        localFeignClient.createCustomer(createCustomerRequest);
        String name = customer.getName();
        return user;
    }

    @GetMapping("/test")
    public String getTest() {
        return "test work";
    }

    private final StringKeyGenerator stateGenerator = new Base64StringKeyGenerator(Base64.getUrlEncoder());

//    public void auth(@Value("${clientId}") String clientId, ) {
//        String redirectUrl = "http://localhost:8089/feign"
//        var oAuth2Properties = gitProviderConfiguration.getOAuth2Properties(gitProvider);
//        return OAuth2AuthorizationRequest.authorizationCode()
//                .authorizationUri(oAuth2Properties.getAuthorizationEndpoint())
//                .redirectUri(redirectUri)
//                .clientId(oAuth2Properties.getClientId())
//                .scope(oAuth2Properties.getScopes())
//                .state(stateGenerator.generateKey())
//                .additionalParameters(Map.of(USER_ID_PARAMETER_NAME, currentUser.getId()))
//                .build();
//    }

}

package com.tsyrkunou.jmpwep.application.feign;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsyrkunou.jmpwep.application.converter.ApplicationConverter;
import com.tsyrkunou.jmpwep.application.model.github.GithubContent;
import com.tsyrkunou.jmpwep.application.model.github.GithubRepository;
import com.tsyrkunou.jmpwep.application.model.github.GithubUser;
import com.tsyrkunou.jmpwep.application.service.customerservice.CustomerService;
import com.tsyrkunou.jmpwep.application.utils.softexecutor.IntegrationExceptionCases;
import com.tsyrkunou.jmpwep.application.utils.softexecutor.Try;

import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/feign")
@RestController
public class GitFeginClientController {
    private final GithubFeignClient githubFeignClient;
    private final CustomerService customerService;
    private final ApplicationConverter applicationConverter;
    @Value("${custom.items.token}")
    private String token;

    @GetMapping("/user")
    @SneakyThrows
    public GithubUser getSomething(@RequestParam(required = false) String subToken) {

        String priorityToken = extractPriorityToken(subToken);

        return Try.execute(() -> githubFeignClient.getUser(bearer(priorityToken)))
                .handling(IntegrationExceptionCases.commonIntegrationErrors(this.getClass().getName()))
                .handling(IntegrationExceptionCases.feignException(":"))
                .execute();
    }

    @GetMapping(value = "/repo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GithubRepository getRepo(@RequestParam String subToken,
                                    @RequestParam(required = false) String owner,
                                    @RequestParam(required = false) String repo) {
        String priorityToken = extractPriorityToken(subToken);
        String o = owner == null ? "ANTONAZZOT" : owner;
        String r = repo == null ? "k8s" : repo;

        return Try.execute(() -> githubFeignClient.getRepo(bearer(priorityToken), o, r))
                .handling(IntegrationExceptionCases.feignException(o))
                .execute();

    }

    @GetMapping(value = "/fork", produces = MediaType.APPLICATION_JSON_VALUE)
    public GithubRepository fork(@RequestParam(required = false) String subToken,
                                 @RequestParam(required = false) String owner,
                                 @RequestParam(required = false) String repo) {
        String priorityToken = extractPriorityToken(subToken);
        String o = owner == null ? "ANTONAZZOT" : owner;
        String r = repo == null ? "k8s" : repo;

        return Try.execute(() -> githubFeignClient.forkRepository(bearer(priorityToken), o, r))
                .handling(IntegrationExceptionCases.feignException(o))
                .execute();
    }

    @GetMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GithubContent> getContent(@RequestParam(required = false) String subToken,
                                          @RequestParam(required = false) String owner,
                                          @RequestParam(required = false) String repo,
                                          @RequestParam(required = false) String file) {

        String priorityToken = extractPriorityToken(subToken);
        String o = owner == null ? "ANTONAZZOT" : owner;
        String r = repo == null ? "k8s" : repo;
        String f = file == null ? "" : file;

        return Try.execute(() -> githubFeignClient.getContent(bearer(priorityToken), o, r, f))
                .handling(IntegrationExceptionCases.feignException(o))
                .execute();
    }

    private String bearer(String priorityToken) {
        return "Bearer " + priorityToken;
    }

    private String extractPriorityToken(String subToken) {
        return StringUtil.isEmpty(subToken) ? token : subToken;
    }

    @GetMapping("/test")
    public String getTest() {
        return "new test work";
    }
}

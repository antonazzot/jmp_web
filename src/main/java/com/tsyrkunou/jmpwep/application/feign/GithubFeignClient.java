package com.tsyrkunou.jmpwep.application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.tsyrkunou.jmpwep.application.model.github.GithubRepository;
import com.tsyrkunou.jmpwep.application.model.github.GithubUser;

@FeignClient(name = "github", url = "https://api.github.com")
public interface GithubFeignClient {
    @GetMapping(value = "/user")
    GithubUser getUser(@RequestHeader("Authorization") String authorization);

    @GetMapping(value = "/login/oauth/access_token")
    String getAuthorization();

    @PostMapping(value = "/repos/{owner}/{repo}/forks")
    GithubRepository forkRepository(@RequestHeader("Authorization") String authorization,
                                  @PathVariable("owner") String owner,
                                  @PathVariable("repo") String repo);


}
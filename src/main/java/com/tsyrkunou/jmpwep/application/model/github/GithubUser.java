package com.tsyrkunou.jmpwep.application.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GithubUser {

    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty("login")
    private String username;
}

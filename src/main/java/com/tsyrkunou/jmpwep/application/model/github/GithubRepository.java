package com.tsyrkunou.jmpwep.application.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepository {

    @JsonProperty("id")
    private Long gitId;

    private GithubUser owner;

    @JsonProperty("name")
    private String gitName;

    @JsonProperty("clone_url")
    private String gitUrl;

    @JsonProperty("default_branch")
    private String defaultBranch;

    @JsonProperty("parent")
    private GithubRepository parentRepository;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("forks_count")
    private Long forksCount;
}
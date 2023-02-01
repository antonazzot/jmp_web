/*
 * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
 * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
 * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
 * unless prior written permission is obtained from EPAM Systems, Inc
 */
package com.tsyrkunou.jmpwep.application.utils.exceptionhandlers;

import com.tsyrkunou.jmpwep.application.model.github.GithubRepository;

import lombok.Getter;

@Getter
public class InsufficientRepositoryRightsException extends GitException {

    private final transient GithubRepository gitRepositoryData;

    private final String[] rights;

    public InsufficientRepositoryRightsException(GithubRepository gitRepositoryData, String[] rights) {
        super("git-integration.insufficient.rights", rights, gitRepositoryData.getGitName());
        this.gitRepositoryData = gitRepositoryData;
        this.rights = rights;
    }

    public InsufficientRepositoryRightsException(Throwable e, GithubRepository gitRepositoryData, String[] rights) {
        super("git-integration.insufficient.rights", e, rights, gitRepositoryData.getGitName());
        this.gitRepositoryData = gitRepositoryData;
        this.rights = rights;
    }
}

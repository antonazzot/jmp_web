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
public class RepositoryNotFoundOrInaccessibleException extends GitException {

    private final transient GithubRepository gitRepositoryData;

    public RepositoryNotFoundOrInaccessibleException(GithubRepository gitRepositoryData) {
        super("git-integration.repository.not-found-or-inaccessible", gitRepositoryData.getGitName());
        this.gitRepositoryData = gitRepositoryData;
    }

    public RepositoryNotFoundOrInaccessibleException(Throwable e, GithubRepository gitRepositoryData) {
        super("git-integration.repository.not-found-or-inaccessible", e, gitRepositoryData.getGitName());
        this.gitRepositoryData = gitRepositoryData;
    }
}

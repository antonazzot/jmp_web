/*
 * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
 * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
 * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
 * unless prior written permission is obtained from EPAM Systems, Inc
 */
package com.tsyrkunou.jmpwep.application.utils.softexecutor;

import com.tsyrkunou.jmpwep.application.model.github.GithubRepository;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.InsufficientRepositoryRightsException;
import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.RepositoryNotFoundOrInaccessibleException;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GitExceptionCases {

    public static Try.ConsumerHandler forbidden(GithubRepository gitRepositoryData, String... rights) {
        return new Try.ConsumerHandler(
                FeignException.Forbidden.class,
                e -> {
                    throw new InsufficientRepositoryRightsException(e, gitRepositoryData, rights);
                }
        );
    }

    public static Try.ConsumerHandler repositoryNotFound(GithubRepository gitRepositoryData) {
        return new Try.ConsumerHandler(
                FeignException.NotFound.class,
                e -> {
                    throw new RepositoryNotFoundOrInaccessibleException(e, gitRepositoryData);
                }
        );
    }
}

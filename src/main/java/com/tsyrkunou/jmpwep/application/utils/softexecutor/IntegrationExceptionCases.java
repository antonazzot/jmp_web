/*
 * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
 * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
 * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
 * unless prior written permission is obtained from EPAM Systems, Inc
 */
package com.tsyrkunou.jmpwep.application.utils.softexecutor;

import java.net.SocketTimeoutException;

import com.tsyrkunou.jmpwep.application.utils.exceptionhandlers.IntegrationUnexpectedException;

import feign.FeignException;
import feign.RetryableException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class IntegrationExceptionCases {

    public static Try.ConsumerHandler[] commonIntegrationErrors(String integrationChannel) {
        return new Try.ConsumerHandler[]{
                timedOut(integrationChannel),
                internalServerError(integrationChannel),
                notImplemented(integrationChannel),
                badGateway(integrationChannel),
                serviceUnavailable(integrationChannel),
                gatewayTimeout(integrationChannel)
        };
    }

    public static Try.ConsumerHandler timedOut(String integrationChannel) {
        return new Try.ConsumerHandler(
                RetryableException.class,
                e -> {
                    if (e.getCause() instanceof SocketTimeoutException) {
                        integrationUnexpected(integrationChannel, e, "Timeout");
                    } else {
                        integrationUnexpected(integrationChannel, e, "Undefined");
                    }
                }
        );
    }

    public static Try.ConsumerHandler internalServerError(String integrationChannel) {
        return new Try.ConsumerHandler(
                FeignException.InternalServerError.class,
                e -> integrationUnexpected(integrationChannel, e, "500")
        );
    }

    public static Try.ConsumerHandler notImplemented(String integrationChannel) {
        return new Try.ConsumerHandler(
                FeignException.NotImplemented.class,
                e -> integrationUnexpected(integrationChannel, e, "501")
        );
    }

    public static Try.ConsumerHandler badGateway(String integrationChannel) {
        return new Try.ConsumerHandler(
                FeignException.BadGateway.class,
                e -> integrationUnexpected(integrationChannel, e, "502")
        );
    }

    public static Try.ConsumerHandler serviceUnavailable(String integrationChannel) {
        return new Try.ConsumerHandler(
                FeignException.ServiceUnavailable.class,
                e -> integrationUnexpected(integrationChannel, e, "503")
        );
    }

    public static Try.ConsumerHandler gatewayTimeout(String integrationChannel) {
        return new Try.ConsumerHandler(
                FeignException.GatewayTimeout.class,
                e -> integrationUnexpected(integrationChannel, e, "504")
        );
    }

    private static void integrationUnexpected(String integrationChannel, Throwable e, String code)
            throws IntegrationUnexpectedException {
        throw new IntegrationUnexpectedException("integration.unexpected-error", e, integrationChannel, code);
    }

    public static Try.ConsumerHandler feignException(String resource) {
        return new Try.ConsumerHandler(
                FeignException.class,
                e -> {
                    log.error("Unexpected {} error. Code: {}. Message: {}",
                            resource, ((FeignException) e).status(), e.getMessage());
                    throw feignIntegrationUnexpectedException(resource, e);
                }
        );
    }

    public static IntegrationUnexpectedException feignIntegrationUnexpectedException(String resource) {
        return new IntegrationUnexpectedException("integration.unexpected-error-without-code", resource);
    }

    public static IntegrationUnexpectedException feignIntegrationUnexpectedException(String resource, Throwable e) {
        return new IntegrationUnexpectedException("integration.unexpected-error-without-code", e, resource);
    }
}

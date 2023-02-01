/*
 * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
 * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
 * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
 * unless prior written permission is obtained from EPAM Systems, Inc
 */
package com.tsyrkunou.jmpwep.application.utils.exceptionhandlers;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SingleValidationException extends RuntimeException {

    private final String code;

    private final Serializable[] args;

    public SingleValidationException(String code, Serializable... args) {
        super(code);
        this.code = code;
        this.args = args.clone();
    }

    public SingleValidationException(String code, Throwable e, Serializable... args) {
        super(code, e);
        this.code = code;
        this.args = args.clone();
    }
}

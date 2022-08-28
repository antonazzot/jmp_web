package com.tsyrkunou.jmpwep.application.utils.exceptionhandlers;

import java.util.stream.Collectors;

import com.google.common.base.Throwables;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionChainUtils {
    public static String getMessageChain(Exception ex) {
        return Throwables.getCausalChain(ex)
                .stream()
                .map(Throwable::toString)
                .collect(Collectors.joining(" > "));
    }
}

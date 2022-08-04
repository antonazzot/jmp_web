package com.tsyrkunou.jmpwep.application.utils;

import lombok.experimental.UtilityClass;

import java.net.URI;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@UtilityClass
public final class ResourceUtils {

    public static URI buildLocationUri(Class<?> controllerClass, Object... variables) {
        return MvcUriComponentsBuilder
                .fromController(controllerClass)
                .path("/{id}")
                .buildAndExpand(variables)
                .toUri();
    }
}
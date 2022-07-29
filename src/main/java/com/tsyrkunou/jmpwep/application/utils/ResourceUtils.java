package com.tsyrkunou.jmpwep.application.utils;

import java.net.URI;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public final class ResourceUtils {

    private ResourceUtils() {
        // utility class
    }

    public static URI buildLocationUri(Class<?> controllerClass, Object... variables) {
        return MvcUriComponentsBuilder
                .fromController(controllerClass)
                .path("/{id}")
                .buildAndExpand(variables)
                .toUri();
    }
}
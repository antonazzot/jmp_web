package com.tsyrkunou.jmpwep.application.utils;

import java.util.Optional;

import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SpecificationUtils {

    public static <T> Specification<T> isFieldEqualsTo(String field, Object fieldValue) {
        return (root, query, builder) -> Optional.ofNullable(fieldValue)
                .map(val -> builder.equal(pathFromRoot(root, field), val))
                .orElse(null);
    }

    private static Path pathFromRoot(Path root, String alias) {
        var path = root;
        for (String s : alias.split("\\.")) {
            path = path.get(s);
        }
        return path;
    }
}
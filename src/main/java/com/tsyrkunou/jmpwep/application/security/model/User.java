package com.tsyrkunou.jmpwep.application.security.model;

import java.util.Collection;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public abstract class User {
    protected Long id;
    protected Collection<Authority> authorities;
    protected String token;

    protected abstract String getName();

    protected abstract String getEmail();
}

package com.tsyrkunou.jmpwep.application.security.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Data
public abstract class User {
    protected Long id;
    protected Collection<Authority> authorities;
    protected String token;
}

package com.tsyrkunou.jmpwep.application.security.jwt;

import com.tsyrkunou.jmpwep.application.repository.JpaAndSpecificationRepository;
import com.tsyrkunou.jmpwep.application.security.model.UserToken;

import io.micrometer.core.annotation.Timed;

@Timed
public interface UserTokenRepository extends JpaAndSpecificationRepository<UserToken, Long> {
}

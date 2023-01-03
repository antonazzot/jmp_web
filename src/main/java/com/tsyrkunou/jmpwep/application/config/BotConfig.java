package com.tsyrkunou.jmpwep.application.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("bot.config")
public class BotConfig {
    private String botName;
    private String botOwner;
}

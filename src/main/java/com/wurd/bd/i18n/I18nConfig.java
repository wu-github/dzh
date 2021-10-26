package com.wurd.bd.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Collections;
import java.util.Locale;

@Component
@Slf4j
public class I18nConfig {

    @Value("${spring.messages.default}")
    private String defaultLang;

    @Bean
    public LocaleResolver localeResolver() {
        log.info("default lang:", defaultLang);
        String[] defaultLangs = defaultLang.split("_");
        String[] lang = {"en", "US"};
        if (ArrayUtils.isNotEmpty(defaultLangs) || defaultLangs.length >= 2) {
            lang = defaultLangs;
        }
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("i18n.lang");
        localeResolver.setDefaultLocale(new Locale(lang[0], lang[1]));
        return localeResolver;
    }

}

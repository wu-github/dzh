package com.wurd.bd.i18n;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Component
public class I18nConfig {

    @Value("${spring.messages.default}")
    private String defaultLang;

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("i18n.lang");
        localeResolver.setDefaultLocale(new Locale(defaultLang));
        return localeResolver;
    }

}

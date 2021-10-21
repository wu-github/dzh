package com.wurd.bd.i18n;

import com.wurd.bd.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code) {
        return getMessage(code, null, null);

    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, null);

    }

    public String getMessage(String code, Object[] args, Locale locale) {
        if (StringUtils.isEmpty(code)) {
            return code;
        }
        try {
            if (locale == null) {
                locale = LocaleContextHolder.getLocale();
            }
            return messageSource.getMessage(code, args, Constants.ERROR_500_MSG, locale);
        } catch (Exception e) {
            e.printStackTrace();
            return code;
        }
    }

}

package com.wurd.bd.i18n;

import com.wurd.bd.constants.Constants;
import com.wurd.bd.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestController
@RequestMapping("/i18n")
public class I18nController {

    @Autowired
    private MessageUtil messageUtil;

    @RequestMapping("/lang")
    public CommonVo changeLanauage(HttpServletRequest request, HttpServletResponse response, String lang) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (Locale.SIMPLIFIED_CHINESE.toString().equalsIgnoreCase(lang)) {
            localeResolver.setLocale(request, response, new Locale("zh", "CN"));
        } else if (Locale.US.toString().equalsIgnoreCase(lang)) {
            localeResolver.setLocale(request, response, new Locale("en", "US"));
        } else {
            return CommonVo.error(messageUtil.getMessage(Constants.ERROR_UNSUPPORTED_MSG));
        }
        return CommonVo.success(null);
    }

}

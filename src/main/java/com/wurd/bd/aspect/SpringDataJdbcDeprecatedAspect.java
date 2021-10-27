package com.wurd.bd.aspect;

import com.wurd.bd.exception.CommonException;
import com.wurd.bd.i18n.MessageUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SpringDataJdbcDeprecatedAspect {

    @Autowired
    private MessageUtil messageUtil;

    @Pointcut("execution(* com.wurd.bd.service.springData.jdcb.impl.*.*(..))")
    public void point(){}

    @Before("point()")
    public CommonException before() throws CommonException {

        throw new CommonException(messageUtil.getMessage("deprecated"));
    }

}

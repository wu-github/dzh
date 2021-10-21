package com.wurd.bd.advice;

import com.wurd.bd.exception.CommonException;
import com.wurd.bd.constants.Constants;
import com.wurd.bd.i18n.MessageUtil;
import com.wurd.bd.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public CommonVo customGenericExceptionHnadler(CommonException e) {
        e.printStackTrace();
        return CommonVo.error(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonVo customGenericExceptionHnadler(Exception e) {
        e.printStackTrace();
        return CommonVo.error(messageUtil.getMessage(Constants.ERROR_500_MSG));
    }
}

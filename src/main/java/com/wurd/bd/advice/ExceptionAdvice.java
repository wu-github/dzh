package com.wurd.bd.advice;

import com.wurd.bd.exception.CommonException;
import com.wurd.bd.exception.Constants;
import com.wurd.bd.vo.CommonVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public CommonVo customGenericExceptionHnadler(CommonException e) {
        e.printStackTrace();
        return new CommonVo(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonVo customGenericExceptionHnadler(Exception e) {
        e.printStackTrace();
        return new CommonVo(Constants.ERROR_500_MSG);
    }
}

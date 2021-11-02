package com.wurd.bd.advice;

import com.wurd.bd.constants.Constants;
import com.wurd.bd.exception.CommonException;
import com.wurd.bd.i18n.MessageUtil;
import com.wurd.bd.vo.CommonVo;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public CommonVo customExceptionHandler(CommonException e) {
        e.printStackTrace();
        return CommonVo.error(e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonVo customExceptionHandler(MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
        e.printStackTrace();
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        CommonVo vo = CommonVo.error(errors.get(0));
        return vo;
    }

    @ExceptionHandler({ServletException.class, TypeMismatchException.class})
    @ResponseBody
    public CommonVo customServletExceptionHandler(Exception e) {
        e.printStackTrace();
        return CommonVo.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonVo customExceptionHandler(Exception e) {
        e.printStackTrace();
        return CommonVo.error(messageUtil.getMessage(Constants.ERROR_500_MSG));
    }
}

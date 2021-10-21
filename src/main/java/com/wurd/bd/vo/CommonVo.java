package com.wurd.bd.vo;

import com.wurd.bd.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonVo<T> {
    public int code;
    public String msg;
    public T result;

    public static CommonVo error(String msg){
        CommonVo vo = new CommonVo();
        vo.setCode(Constants.ERROR_500);
        vo.setMsg(msg);
        return  vo;
    }

    public static CommonVo success(String msg){
        CommonVo vo = new CommonVo();
        vo.setCode(Constants.SUCCESS_200);
        vo.setMsg(msg);
        return  vo;
    }
}

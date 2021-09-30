package com.wurd.bd.vo;

import com.wurd.bd.exception.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonVo {
    public int code = Constants.ERROR_500;
    public String msg;
    public CommonVo(String msg){
        this.msg = msg;
    }
}

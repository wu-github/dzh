package com.wurd.bd.exception;

import com.wurd.bd.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends Exception {
    public int code = Constants.ERROR_500;
    public String msg;

    public CommonException(String msg) {
        this.msg = msg;
    }
}

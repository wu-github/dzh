package com.wurd.bd.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends Exception{
    public int code;
    public String msg;
}

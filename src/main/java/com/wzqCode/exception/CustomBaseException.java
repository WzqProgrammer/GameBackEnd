package com.wzqCode.exception;

import lombok.Data;

import java.io.InterruptedIOException;

/**
 * @author 14188
 * @date 2021/10/20 10 :40
 * @description
 */
@Data
public class CustomBaseException extends RuntimeException{
    protected Integer code;

    protected String msg;

    public CustomBaseException(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}

package com.wzqCode.obj.msg.server.login;

import com.wzqCode.obj.msg.HttpStatus;
import lombok.Data;

@Data
public class SReturnMsg<T> {

    private Integer code;
    private String msg;

    private T data;

    public SReturnMsg(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> SReturnMsg success(T data){
        return new SReturnMsg<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), data);
    }

    public static  SReturnMsg success(){
        return new SReturnMsg<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), null);
    }

    public static <T> SReturnMsg error(T data){
        return new SReturnMsg<>(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg(), data);
    }

    public static SReturnMsg error(){
        return new SReturnMsg<>(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg(), null);
    }

    public static SReturnMsg error(Integer code, String msg){
        return new SReturnMsg<>(code, msg, null);
    }
}

package com.wzqCode.config;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.server.SReturnMsg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 14188
 * @date 2021/10/20 10 :36
 * @description
 */

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {

    @ExceptionHandler(CustomBaseException.class)
    public SReturnMsg handleHttpMessageNotReadableException(CustomBaseException ex){
        //直接向客户端返回消息
        return SReturnMsg.error(ex.getCode(), ex.getMsg());
    }
}

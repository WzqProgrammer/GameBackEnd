package com.wzqCode.exception.gameException;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

public class NotLoginErrorException extends CustomBaseException {
    public NotLoginErrorException() {
        super(HttpStatus.NOT_LOGIN_ERROR.getCode(), HttpStatus.NOT_LOGIN_ERROR.getMsg());
    }
}

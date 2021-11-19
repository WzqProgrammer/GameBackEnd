package com.wzqCode.exception.gameException;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

public class IdErrorException extends CustomBaseException {
    public IdErrorException() {
        super(HttpStatus.ID_ERROR.getCode(), HttpStatus.ID_ERROR.getMsg());
    }
}

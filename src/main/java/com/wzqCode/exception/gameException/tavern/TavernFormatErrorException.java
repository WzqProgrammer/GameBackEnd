package com.wzqCode.exception.gameException.tavern;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;


public class TavernFormatErrorException extends CustomBaseException {
    public TavernFormatErrorException() {
        super(HttpStatus.TAVERN_FORMAT_ERROR.getCode(), HttpStatus.TAVERN_FORMAT_ERROR.getMsg());
    }
}

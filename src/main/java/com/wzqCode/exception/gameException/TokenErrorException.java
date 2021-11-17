package com.wzqCode.exception.gameException;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;


public class TokenErrorException extends CustomBaseException {

    public TokenErrorException(){
        super(HttpStatus.TOKEN_ERROR.getCode(), HttpStatus.TOKEN_ERROR.getMsg());
    }
}

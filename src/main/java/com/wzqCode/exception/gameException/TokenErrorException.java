package com.wzqCode.exception.gameException;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

/**
 * @author 14188
 * @date 2021/10/20 10 :45
 * @description
 */
public class TokenErrorException extends CustomBaseException {
    public TokenErrorException(){
        super(HttpStatus.TOKEN_ERROR.getCode(), HttpStatus.TOKEN_ERROR.getMsg());
    }
}

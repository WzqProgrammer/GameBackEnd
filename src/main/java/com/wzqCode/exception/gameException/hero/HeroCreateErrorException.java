package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

/**
 * @author 14188
 * @date 2021/11/16 15 :48
 * @description
 */
public class HeroCreateErrorException extends CustomBaseException {
    public HeroCreateErrorException() {
        super(HttpStatus.HERO_CREATE_ERROR.getCode(), HttpStatus.HERO_CREATE_ERROR.getMsg());
    }
}

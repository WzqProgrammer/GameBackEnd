package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

/**
 * @author 14188
 * @date 2021/11/17 14 :28
 * @description
 */
public class HeroNotFountErrorException extends CustomBaseException {

    public HeroNotFountErrorException() {
        super(HttpStatus.HERO_NOT_FOUNT_ERROR.getCode(), HttpStatus.HERO_NOT_FOUNT_ERROR.getMsg());
    }
}

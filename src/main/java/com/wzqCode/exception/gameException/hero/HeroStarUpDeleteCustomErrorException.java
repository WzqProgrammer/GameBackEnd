package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

public class HeroStarUpDeleteCustomErrorException extends CustomBaseException {
    public HeroStarUpDeleteCustomErrorException() {
        super(HttpStatus.HERO_DELETE_ERROR.getCode(), HttpStatus.HERO_DELETE_ERROR.getMsg());
    }
}

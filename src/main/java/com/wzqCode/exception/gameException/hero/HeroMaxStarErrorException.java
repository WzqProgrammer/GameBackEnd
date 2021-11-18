package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

/**
 * @author 14188
 * @date 2021/11/18 10 :20
 * @description
 */
public class HeroMaxStarErrorException extends CustomBaseException {
    public HeroMaxStarErrorException() {
        super(HttpStatus.HERO_MAX_STAR_ERROR.getCode(), HttpStatus.HERO_MAX_STAR_ERROR.getMsg());
    }
}

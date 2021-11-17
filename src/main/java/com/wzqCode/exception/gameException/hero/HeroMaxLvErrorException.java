package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

/**
 * @author 14188
 * @date 2021/11/17 14 :31
 * @description
 */
public class HeroMaxLvErrorException  extends CustomBaseException {

    public HeroMaxLvErrorException() {
        super(HttpStatus.HERO_MAX_LV_ERROR.getCode(), HttpStatus.HERO_MAX_LV_ERROR.getMsg());
    }
}

package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

public class HeroStarUpMaterialErrorException extends CustomBaseException {
    public HeroStarUpMaterialErrorException() {
        super(HttpStatus.HERO_STAR_UP_MATERIAL_ERROR.getCode(), HttpStatus.HERO_STAR_UP_MATERIAL_ERROR.getMsg());
    }
}

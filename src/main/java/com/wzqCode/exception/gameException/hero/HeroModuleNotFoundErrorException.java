package com.wzqCode.exception.gameException.hero;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

/**
 * @author 14188
 * @date 2021/11/16 15 :37
 * @description
 */
public class HeroModuleNotFoundErrorException extends CustomBaseException {

    public HeroModuleNotFoundErrorException() {
        super(HttpStatus.HERO_MODULE_NOT_FOUNT_ERROR.getCode(), HttpStatus.HERO_MODULE_NOT_FOUNT_ERROR.getMsg());
    }
}

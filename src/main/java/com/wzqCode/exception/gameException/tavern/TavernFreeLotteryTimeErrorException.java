package com.wzqCode.exception.gameException.tavern;

import com.wzqCode.exception.CustomBaseException;
import com.wzqCode.obj.msg.HttpStatus;

public class TavernFreeLotteryTimeErrorException extends CustomBaseException {
    public TavernFreeLotteryTimeErrorException() {
        super(HttpStatus.TAVERN_FREE_LOTTERY_TIME_ERROR.getCode(), HttpStatus.TAVERN_FREE_LOTTERY_TIME_ERROR.getMsg());
    }
}

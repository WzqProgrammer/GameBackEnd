package com.wzqCode.obj.msg.server.tavern;

import lombok.Data;


@Data
public class SGetTavernInfoMsg {

    private Integer lastFreeTime;
    private Integer itemNum;
}

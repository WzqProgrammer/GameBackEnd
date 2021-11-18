package com.wzqCode.obj.msg.client.hero;

import lombok.Data;

@Data
public class CStarUpHeroMsg {

    private Integer heroId;

    // 消耗的本体同星级英雄
    private Integer customHeroId1;
    // 不同的两个的其他狗粮英雄
    private Integer customHeroId2;

    private Integer customHeroId3;
}

package com.wzqCode.obj.module;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hero_config")
public class HeroModule {

    private Integer id;
    private Integer typeId;
    private String name;
    private Integer initAtt;
    private Integer initDef;
    private Integer initHp;
    private Integer lvUpAtt;
    private Integer lvUpDef;
    private Integer lvUpHp;
    private Integer lotteryWeight;
}

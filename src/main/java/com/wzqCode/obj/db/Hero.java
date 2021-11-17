package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hero")
public class Hero extends BaseDBTable{

    private Integer playerId;

    private Integer typeId;

    private String heroName;

    private Integer att;    //攻击

    private Integer def;    //防御

    private Integer maxHp;

    private Integer lv;

    private Integer star;    //星级
}

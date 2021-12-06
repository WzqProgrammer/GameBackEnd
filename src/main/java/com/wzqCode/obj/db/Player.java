package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("player")
public class Player extends BaseDBTable{

    // 账号id
    private Integer accountId;

    //用户昵称
    private String nickname;

    //用户角色等级
    private int level;

    //头像
    private String head;

    //金币数
    private int coin;

    //用户关卡进度
    private int checkpoint;

    // 用户上次免费抽奖的时间
    private String lastFreeLotteryTime;
}

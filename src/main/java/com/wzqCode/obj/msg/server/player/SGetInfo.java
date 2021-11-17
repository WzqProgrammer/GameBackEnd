package com.wzqCode.obj.msg.server.player;

import lombok.Data;

@Data
public class SGetInfo {
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

    // 基于playerId生成的新token
    private String newToken;
}

package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("player")
public class Player {
    @TableId(type= IdType.AUTO)
    private Integer id;

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

    //逻辑删除
    @TableLogic(delval = "1")
    private Integer del;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}

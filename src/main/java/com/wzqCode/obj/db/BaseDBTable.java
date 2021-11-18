package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
public class BaseDBTable {
    // 属性的命名要与数据库中字段的命名相同（mybatis-plus会自动开启驼峰命名转下划线命名的转换）
    // id字段需要注释标记其id的生成方式
    @TableId(type= IdType.AUTO)
    protected Integer id;

    //逻辑删除
    @TableLogic(value = "0", delval = "1")
    protected Integer del;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    protected String createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateTime;
}

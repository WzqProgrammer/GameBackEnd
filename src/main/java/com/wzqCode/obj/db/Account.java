package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("account")     //指定实体类对象与数据库表的关联关系，通过表名称
@ApiModel("用户信息")
public class Account {
    // 属性的命名要与数据库中字段的命名相同（mybatis-plus会自动开启驼峰命名转下划线命名的转换）
    // id字段需要注释标记其id的生成方式
    @TableId(type= IdType.AUTO)
    private Integer id;

    // 账号
    private String account;
    // 密码
    private String password;

    //逻辑删除
    @TableLogic(delval = "1")
    private Integer del;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}

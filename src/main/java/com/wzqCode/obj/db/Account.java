package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("account")     //指定实体类对象与数据库表的关联关系，通过表名称
@ApiModel("用户信息")
public class Account extends BaseDBTable{

    // 账号
    private String account;
    // 密码
    private String password;
}

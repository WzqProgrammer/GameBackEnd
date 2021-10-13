package com.wzqCode.obj.msg.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("客户端登录消息结构")
public class CLoginMsg {
    @ApiModelProperty("用户名")
    private String account;
    @ApiModelProperty("用户密码")
    private String password;
}

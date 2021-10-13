package com.wzqCode.obj.msg.client;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("客户端注册消息结构")
public class CRegMsg {
    private String account;
    private String password;
}

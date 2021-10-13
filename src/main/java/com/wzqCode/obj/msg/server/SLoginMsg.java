package com.wzqCode.obj.msg.server;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("服务端登录返回消息结构")
public class SLoginMsg {
    private String token;
}

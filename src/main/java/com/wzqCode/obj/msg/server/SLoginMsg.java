package com.wzqCode.obj.msg.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务端登录返回消息结构")
public class SLoginMsg {
    @ApiModelProperty("返回码 0为成功 1为失败")
    private Integer ret;
}

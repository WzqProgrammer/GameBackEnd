package com.wzqCode.controller;

import com.wzqCode.obj.msg.server.SReturnMsg;
import com.wzqCode.service.TavernService;
import com.wzqCode.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TavernController {

    @Autowired
    TavernService tavernService;

    // 获取酒馆信息接口
    @PostMapping("/getTavernInfo")
    public SReturnMsg getTavernInfo(HttpServletRequest request){
        Integer playerId = ServletUtil.getPlayerIdByRequest(request);
        return tavernService.getTavernInfo(playerId);
    }
}

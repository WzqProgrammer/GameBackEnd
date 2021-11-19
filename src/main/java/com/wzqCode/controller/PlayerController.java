package com.wzqCode.controller;

import com.wzqCode.obj.msg.server.SReturnMsg;
import com.wzqCode.service.PlayerService;
import com.wzqCode.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    // 获取用户的玩家角色信息接口
    @PostMapping("/getPlayerInfo")
    public SReturnMsg getPlayerInfo(HttpServletRequest request){

       int accountId =  ServletUtil.getAccountIdByRequest(request);

       return playerService.getInfo(accountId);
    }

    // 心跳设置接口
    @PostMapping("/heartBeat")
    public SReturnMsg heartBeat(HttpServletRequest request){
        Integer playerId = ServletUtil.getPlayerIdByRequest(request);
        playerService.procHeartBeat(playerId);
        return SReturnMsg.success();
    }
}

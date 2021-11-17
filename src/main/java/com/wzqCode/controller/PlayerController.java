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

    @PostMapping("/getPlayerInfo")
    public SReturnMsg getPlayerInfo(HttpServletRequest request){

       int accountId =  ServletUtil.getAccountIdByRequest(request);

       return playerService.getInfo(accountId);
    }
}

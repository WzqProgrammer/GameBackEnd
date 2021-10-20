package com.wzqCode.controller;

import com.wzqCode.obj.msg.client.login.CLoginMsg;
import com.wzqCode.obj.msg.client.login.CRegMsg;
import com.wzqCode.obj.msg.server.login.SReturnMsg;
import com.wzqCode.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/register")
    public SReturnMsg register(@RequestBody CRegMsg cRegMsg){
        //调用LoginService中的方法
        return loginService.register(cRegMsg.getAccount(), cRegMsg.getPassword());
    }

    @PostMapping("/login")
    public SReturnMsg login(@RequestBody CLoginMsg cLoginMsg){
        return loginService.login(cLoginMsg.getAccount(), cLoginMsg.getPassword());
    }
}

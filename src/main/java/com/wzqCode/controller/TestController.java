package com.wzqCode.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wzqCode.Utils.JwtUtil;
import com.wzqCode.mapper.AccountMapper;
import com.wzqCode.obj.db.Account;
import com.wzqCode.obj.msg.client.CLoginMsg;
import com.wzqCode.obj.msg.server.SLoginMsg;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    AccountMapper accountMapper;

    @GetMapping("/test")
    @ApiOperation("测试接口")
    public String test(){
        Account account = accountMapper.selectById(10);
        String accountName = account.getAccount();
        String ret = "hello test ok" + accountName;
        return ret;
    }

    @PostMapping("/addAccount")
    @ApiOperation("添加用户信息接口")
    public String addAccount(@RequestBody Account account){
        accountMapper.insert(account);
        return "" + account.getId();
    }

    @PostMapping("/deleteAccountById")
    @ApiOperation("删除用户信息")
    public String deleteAccountById(Integer id){
        accountMapper.deleteById(id);

        return ""+id;
    }

    @PostMapping("/updateAccount")
    @ApiOperation("修改用户信息")
    public String updateAccount(@RequestBody Account account){
        Account original = accountMapper.selectById(account.getId());
        original.setAccount(account.getAccount());
        original.setPassword(account.getPassword());
        accountMapper.updateById(original);
        return ""+original.getId();
    }

    @GetMapping("/verifyToken")
    @ApiOperation("测试token的解析")
    public String verifyToken(String token){
        DecodedJWT decodedJWT = JwtUtil.verify(token);
        Integer id = decodedJWT.getClaim("id").asInt();
        String account = decodedJWT.getClaim("account").asString();
        return "verify token ok : id = " + id + "  account = "+account;
    }
}

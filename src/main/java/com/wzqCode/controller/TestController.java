package com.wzqCode.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wzqCode.exception.gameException.hero.HeroCreateErrorException;
import com.wzqCode.mapper.HeroMapper;
import com.wzqCode.mapper.PlayerMapper;
import com.wzqCode.obj.db.Hero;
import com.wzqCode.obj.db.Player;
import com.wzqCode.obj.msg.HttpStatus;
import com.wzqCode.obj.msg.server.SReturnMsg;
import com.wzqCode.utils.JwtUtil;
import com.wzqCode.mapper.AccountMapper;
import com.wzqCode.obj.db.Account;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class TestController {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    HeroMapper heroMapper;

    @Autowired
    PlayerMapper playerMapper;

    @PostMapping("addPlayerToDB")
    @ApiOperation("添加player数据到数据库接口")
    public SReturnMsg addPlayerToDB(){

        Player player = new Player();
        player.setNickname("");
        player.setHead("");
        player.setLevel(1);
        player.setCoin(100);
        player.setCheckpoint(1);
        player.setAccountId(8);

        try {
            playerMapper.insert(player);
        }catch(Exception e){
            //用户信息插入失败
            return SReturnMsg.error(HttpStatus.PLAYER_CREATE_ERROR.getCode(), HttpStatus.PLAYER_CREATE_ERROR.getMsg());
        }

        return SReturnMsg.success();
    }

    @PostMapping("addHeroToDB")
    @ApiOperation("添加英雄数据到数据库接口")
    public String addHeroToDB(){
        Hero hero = new Hero();
        hero.setPlayerId(8);
        hero.setTypeId(1);
        hero.setHeroName("我的");
        hero.setAtt(100);
        hero.setDef(20);
        hero.setMaxHp(1000);
        hero.setStar(1);
        hero.setLv(1);

        try {
            heroMapper.insert(hero);
        }catch (Exception e){
            throw new HeroCreateErrorException();
        }

        return "OK";
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

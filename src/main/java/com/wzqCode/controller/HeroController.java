package com.wzqCode.controller;

import com.wzqCode.obj.msg.client.hero.CCreateHeroMsg;
import com.wzqCode.obj.msg.client.hero.CLvUpHeroMsg;
import com.wzqCode.obj.msg.client.hero.CStarUpHeroMsg;
import com.wzqCode.obj.msg.server.SReturnMsg;
import com.wzqCode.service.HeroService;
import com.wzqCode.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("hero")
public class HeroController {

    @Autowired
    HeroService heroService;

    // 创建英雄接口
    @PostMapping("/createHero")
    public SReturnMsg createHero(HttpServletRequest request, @RequestBody CCreateHeroMsg cCreateHeroMsg){
        // 调用 HeroService 的 createHero 方法
        Integer playerId = ServletUtil.getPlayerIdByRequest(request);
        heroService.createHero(playerId, cCreateHeroMsg.getHeroTypeId());
        return SReturnMsg.success();
    }

    // 获取当前玩家的英雄列表
    @PostMapping("/getHeroList")
    public SReturnMsg getHeroList(HttpServletRequest request){
        Integer playerId = ServletUtil.getPlayerIdByRequest(request);
        return heroService.getHeroList(playerId);
    }

    // 英雄升级接口
    @PostMapping("/lvUpHero")
    public SReturnMsg lvUpHero(HttpServletRequest request, @RequestBody CLvUpHeroMsg cLvUpHeroMsg){
        Integer playerId = ServletUtil.getPlayerIdByRequest(request);
        return heroService.lvUpHero(playerId, cLvUpHeroMsg.getHeroId());
    }

    // 英雄升星级接口
    @PostMapping("/starUpHero")
    public SReturnMsg starUpHero(HttpServletRequest request, @RequestBody CStarUpHeroMsg cStarUpHeroMsg){
        Integer playerId = ServletUtil.getPlayerIdByRequest(request);
        return heroService.starUpHero(playerId, cStarUpHeroMsg.getHeroId(),
                cStarUpHeroMsg.getCustomHeroId1(), cStarUpHeroMsg.getCustomHeroId2(), cStarUpHeroMsg.getCustomHeroId3());
    }
}

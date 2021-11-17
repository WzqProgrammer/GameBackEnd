package com.wzqCode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.cache.PlayerCache;
import com.wzqCode.config.gameConfig.HeroConfig;
import com.wzqCode.exception.gameException.hero.HeroCreateErrorException;
import com.wzqCode.exception.gameException.hero.HeroMaxLvErrorException;
import com.wzqCode.exception.gameException.hero.HeroModuleNotFoundErrorException;
import com.wzqCode.exception.gameException.hero.HeroNotFountErrorException;
import com.wzqCode.mapper.HeroMapper;
import com.wzqCode.obj.cache.PlayerInfo;
import com.wzqCode.obj.db.Hero;
import com.wzqCode.obj.module.HeroModule;
import com.wzqCode.obj.msg.server.hero.SGetHeroListMsg;
import com.wzqCode.obj.msg.server.SReturnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroService {

    @Autowired
    PlayerCache playerCache;

    @Autowired
    HeroMapper heroMapper;

    @Autowired
    HeroConfig heroConfig;

    // 初始化缓存
    public void initCache(PlayerInfo playerInfo){

        // 访问数据库，获取用户英雄信息
        QueryWrapper<Hero> wrapper = new QueryWrapper<>();
        // 注意 id为player表中的唯一自增id
        wrapper.eq("player_id", playerInfo.getBaseProp().getId());
        List<Hero> heroes = heroMapper.selectList(wrapper);

        playerInfo.setHeroes(heroes);
    }

    // 创建英雄数据
    public void createHero(Integer playerId, Integer heroTypeId){
        // 从模板列表中获取该heroTypeId对应的影响模板
        HeroModule heroModule = heroConfig.getHeroModuleById(heroTypeId);
        if (heroModule == null) {
            //抛出英雄模板未找到异常
            throw new HeroModuleNotFoundErrorException();
        }

        // 将heroModule中的属性赋值给hero
        Hero hero = new Hero();
        hero.setPlayerId(playerId);
        hero.setTypeId(heroModule.getTypeId());
        hero.setHeroName(heroModule.getName());
        hero.setAtt(heroModule.getAtt());
        hero.setDef(heroModule.getDef());
        hero.setMaxHp(heroModule.getHp());
        hero.setLv(1);
        hero.setStar(1);

        //先将数据插入数据库
        try {
            heroMapper.insert(hero);
        }catch (Exception e){
            throw new HeroCreateErrorException();
        }

        // 缓存
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        playerInfo.getHeroes().add(hero);
    }

    // 获取当前玩家的英雄列表
    public SReturnMsg getHeroList(Integer playerId){
        //从缓存中读取当前玩家的英雄列表
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        SGetHeroListMsg sGetHeroListMsg = new SGetHeroListMsg();
        sGetHeroListMsg.setHeroList(heroes);
        return SReturnMsg.success(sGetHeroListMsg);
    }

    // 英雄升级
    public SReturnMsg lvUpHero(Integer playerId, Integer heroId){
        Hero hero = getHeroByPlayerId(playerId, heroId);
        // 未找到对应的英雄，抛出异常
        if (hero == null)
            throw new HeroNotFountErrorException();

        Integer curMaxLv = hero.getStar() * heroConfig.getStarLv();
        // 英雄已达当前最大等级，抛出异常
        if(hero.getLv() >= curMaxLv)
            throw new HeroMaxLvErrorException();

        // TODO: 判断升级道具是否足够，执行道具相关逻辑
        // 英雄等级+1，相应属性变更
        HeroModule heroModule = heroConfig.getHeroModuleById(hero.getTypeId());

        hero.setLv(hero.getLv() + 1);
        hero.setAtt(hero.getAtt() + heroModule.getLvUpAtt());
        hero.setDef(hero.getDef() + heroModule.getLvUpDef());
        hero.setMaxHp(hero.getMaxHp() + heroModule.getLvUpHp());

        return SReturnMsg.success(hero);
    }

    // 获取当前玩家需要的升级的英雄
    private Hero getHeroByPlayerId(Integer playerId, Integer heroId){
        // 获取当前玩家的所有英雄
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        // 根据英雄id获取需要升级的英雄
        for (Hero hero : heroes) {
            if(hero.getId().equals(heroId))
                return hero;
        }
        return null;
    }

}

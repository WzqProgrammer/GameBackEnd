package com.wzqCode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.cache.PlayerCache;
import com.wzqCode.config.gameConfig.HeroConfig;
import com.wzqCode.exception.gameException.hero.*;
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

    // 英雄升星
    public SReturnMsg starUpHero(Integer playerId, Integer heroId, Integer customHeroId1, Integer customHeroId2,Integer customHeroId3){
        Hero hero = getHeroByPlayerId(playerId, heroId);
        // 未找到对应的英雄，抛出异常

        // 当前英雄已达最大星级
        if(hero.getStar() >= heroConfig.getMaxStar())
            throw new HeroMaxStarErrorException();

        // TODO: 判断升级道具是否足够，执行道具相关逻辑

        // 需要的狗粮英雄是否足够，需要一个同星级的本体英雄和两个同星级狗粮
        Hero customHero1 = getHeroByPlayerId(playerId, customHeroId1);
        Hero customHero2 = getHeroByPlayerId(playerId, customHeroId2);
        Hero customHero3 = getHeroByPlayerId(playerId, customHeroId3);

        // 判断英雄的星级
        if(!customHero1.getTypeId().equals(hero.getTypeId()) || !customHero1.getStar().equals(hero.getStar()) ||
            !customHero2.getStar().equals(hero.getStar()) || !customHero3.getStar().equals(hero.getStar()))
            throw new HeroStarUpMaterialErrorException();

        // 数据库删除消耗的材料英雄
        try{
            heroMapper.deleteById(customHeroId1);
            heroMapper.deleteById(customHeroId2);
            heroMapper.deleteById(customHeroId3);
        }catch (Exception e){
            throw new HeroStarUpDeleteCustomErrorException();
        }

        // 删除缓存中的相关英雄数据
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroList = playerInfo.getHeroes();

        heroList.remove(customHero1);
        heroList.remove(customHero2);
        heroList.remove(customHero3);

        for (Hero item : heroList) {
            if(item.getId().equals(customHeroId1)){
                heroList.remove(item);
                break;
            }
        }

        // 更新缓存数据
        hero.setStar(hero.getStar()+1);
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
        throw new HeroNotFountErrorException();
    }
}

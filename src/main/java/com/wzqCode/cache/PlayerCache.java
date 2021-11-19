package com.wzqCode.cache;

import com.wzqCode.exception.gameException.IdErrorException;
import com.wzqCode.mapper.HeroMapper;
import com.wzqCode.mapper.PlayerMapper;
import com.wzqCode.obj.cache.PlayerInfo;
import com.wzqCode.obj.db.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class PlayerCache {

    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    HeroMapper heroMapper;

    // 线程安全的hashMap，将player_id与用户内存属性做映射，需要创建PlayerInfo结构
    private ConcurrentHashMap<Integer, PlayerInfo> playerInfoMap = new ConcurrentHashMap<>();

    public PlayerInfo getPlayerInfo(Integer playerId){
        try{
            return playerInfoMap.get(playerId);
        }catch (Exception e){
            throw new IdErrorException();
        }
    }

    public ConcurrentHashMap<Integer, PlayerInfo> getPlayerInfoMap(){ return playerInfoMap; }

    // 添加相应的缓存到hashMap中
    public void addPlayerInfo(PlayerInfo playerInfo){
        playerInfoMap.put(playerInfo.getBaseProp().getId(), playerInfo);
    }

    //将制定用户保存在数据库中
    public void saveOne(PlayerInfo playerInfo){
        //依次将PlayerInfo中每个模块缓存更新到数据库中
        //保存英雄属性
        playerMapper.updateById(playerInfo.getBaseProp());

        // 保存英雄数据
        for(Hero hero: playerInfo.getHeroes()){
            heroMapper.updateById(hero);
        }
    }

    //将所有用户数据保存到数据库中
    public void saveAll(){
    }
}

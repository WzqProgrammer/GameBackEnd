package com.wzqCode.cache;

import com.wzqCode.obj.cache.PlayerInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class PlayerCache {

    // 线程安全的hashMap，将player_id与用户内存属性做映射，需要创建PlayerInfo结构
    // player_id 即对应的 account_id
    private ConcurrentHashMap<Integer, PlayerInfo> playerInfos = new ConcurrentHashMap<>();

    public PlayerInfo getPlayerInfo(Integer playerId){
        return playerInfos.get(playerId);
    }

    // 添加相应的缓存到hashMap中
    public void addPlayerInfo(PlayerInfo playerInfo){
        playerInfos.put(playerInfo.getBaseProp().getId(), playerInfo);
    }
    //将制定用户保存在数据库中
    public void saveOne(PlayerInfo playerInfo){
        //依次将PlayerInfo中每个模块缓存更新到数据库中
    }

    //将所有用户数据保存到数据库中
    public void saveAll(){
    }
}

package com.wzqCode.scheduler;

import com.wzqCode.cache.PlayerCache;
import com.wzqCode.obj.cache.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Scheduler {

    @Autowired
    PlayerCache playerCache;

    // 2分钟没有收到心跳就认为用户掉线或登出
//    private final Integer LOGIN_OUT_TIME = 2*60*1000;
    private final Integer LOGIN_OUT_TIME = 20*1000;  //测试数据

    // 定时检测用户心跳 5分钟
//    @Scheduled(fixedRate = 5*60*1000)
    @Scheduled(fixedRate = 30*1000)
    public void heartBeatTask(){
        long curTime = System.currentTimeMillis();
        ConcurrentHashMap<Integer, PlayerInfo> playerInfoMap = playerCache.getPlayerInfoMap();
        // 遍历当前所有用户，判断心跳时间
        for(PlayerInfo playerInfo: playerInfoMap.values()){
            if(playerInfo.getLastHeartBeatTime() + LOGIN_OUT_TIME < curTime){
                System.out.println("用户："  + playerInfo.getBaseProp().getId() + "下线");
                // 将该用户信息更新到数据库
                playerCache.saveOne(playerInfo);
                // 内存中移除该用户
                playerInfoMap.remove(playerInfo.getBaseProp().getId());
            }
        }
    }

    /**
     * 保存所有用户信息到数据库
     */
    @Scheduled(fixedRate = 60*60*1000)
    public void saveAll(){
        playerCache.saveAll();
    }
}

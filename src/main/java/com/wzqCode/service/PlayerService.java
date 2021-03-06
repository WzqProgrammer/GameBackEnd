package com.wzqCode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.cache.PlayerCache;
import com.wzqCode.mapper.PlayerMapper;
import com.wzqCode.obj.cache.PlayerInfo;
import com.wzqCode.obj.db.Player;
import com.wzqCode.obj.msg.HttpStatus;
import com.wzqCode.obj.msg.server.SReturnMsg;
import com.wzqCode.obj.msg.server.player.SGetInfo;
import com.wzqCode.utils.DataUtil;
import com.wzqCode.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PlayerService {

    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    HeroService heroService;

    // 获取用户基础信息
    public SReturnMsg getInfo(Integer accountId){
        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", accountId);
        Player player = playerMapper.selectOne(queryWrapper);

        //此时该用户还没有角色信息
        if(player==null){
            player = new Player();
            player.setNickname("");
            player.setHead("");
            player.setLevel(1);
            player.setCoin(100);
            player.setCheckpoint(1);
            player.setAccountId(accountId);
            // 新创建的用户直接有一次免费抽奖的机会
            // 设置免费抽奖时间
            SimpleDateFormat df = new SimpleDateFormat(DataUtil.YYYY_MM_DD_HH_MM_SS);
            String initTime = df.format(0);
            player.setLastFreeLotteryTime(initTime);

            try {
                playerMapper.insert(player);
            }catch(Exception e){
                //用户信息插入失败
                return SReturnMsg.error(HttpStatus.PLAYER_CREATE_ERROR.getCode(), HttpStatus.PLAYER_CREATE_ERROR.getMsg());
            }
        }

        initCache(player);

        String newToken = JwtUtil.createPlayerToken(player.getId());

        //向用户返回角色信息
        SGetInfo sGetInfo = new SGetInfo();
        sGetInfo.setNickname(player.getNickname());
        sGetInfo.setCheckpoint(player.getCheckpoint());
        sGetInfo.setCoin(player.getCoin());
        sGetInfo.setHead(player.getHead());
        sGetInfo.setLevel(player.getLevel());
        sGetInfo.setNewToken(newToken);

        return SReturnMsg.success(sGetInfo);
    }

    // 心跳时间处理
    public void procHeartBeat(Integer playerId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);

        Long curTime = System.currentTimeMillis();
        playerInfo.setLastHeartBeatTime(curTime);
    }

    // 加载数据库信息到缓存
    private void initCache(Player player){
        // 加载用户基础信息到缓存
        PlayerInfo playerInfo = playerCache.getPlayerInfo(player.getId(), false);

        if(playerInfo != null) {
            return;
        }

        playerInfo = new PlayerInfo();
        playerInfo.setBaseProp(player);

        // 初始时的心跳赋值
        playerInfo.setLastHeartBeatTime(System.currentTimeMillis());

        // 加载用户英雄信息到缓存
        // 调用英雄初始化缓存，在HeroService中
        heroService.initCache(playerInfo);

        playerCache.addPlayerInfo(playerInfo);
        //加载其他模块到缓存
    }
}

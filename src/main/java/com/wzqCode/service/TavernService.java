package com.wzqCode.service;

import com.wzqCode.cache.PlayerCache;
import com.wzqCode.config.gameConfig.GlobalConfig;
import com.wzqCode.config.gameConfig.HeroConfig;
import com.wzqCode.exception.gameException.tavern.TavernFormatErrorException;
import com.wzqCode.exception.gameException.tavern.TavernFreeLotteryTimeErrorException;
import com.wzqCode.obj.cache.PlayerInfo;
import com.wzqCode.obj.db.Player;
import com.wzqCode.obj.msg.server.SReturnMsg;
import com.wzqCode.obj.msg.server.tavern.SFreeLotteryMsg;
import com.wzqCode.obj.msg.server.tavern.SGetTavernInfoMsg;
import com.wzqCode.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@Slf4j
public class TavernService {

    @Autowired
    PlayerCache playerCache;

    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    HeroConfig heroConfig;

    @Autowired
    HeroService heroService;

    // 获取酒馆信息
    public SReturnMsg getTavernInfo(Integer playerId) {

        Integer lastFreeTime = getLastFreeTime(playerId);
        if(lastFreeTime < 0)
            lastFreeTime = 0;
        // TODO: 查询卷轴道具数量，需要道具系统，先设置为0

        SGetTavernInfoMsg sGetTavernInfoMsg = new SGetTavernInfoMsg();
        sGetTavernInfoMsg.setLastFreeTime(lastFreeTime);
        sGetTavernInfoMsg.setItemNum(0);
        return SReturnMsg.success(sGetTavernInfoMsg);
    }

    // 用户免费抽取英雄
    public SReturnMsg freeLottery(Integer playerId){
        Integer lastFreeTime = getLastFreeTime(playerId);
        // 还未到免费时间
        if(lastFreeTime > 0)
            throw new TavernFreeLotteryTimeErrorException();

        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseProp = playerInfo.getBaseProp();

        // 设置免费抽奖时间为当前时间
        SimpleDateFormat df = new SimpleDateFormat(DataUtil.YYYY_MM_DD_HH_MM_SS);
        String currentTime = df.format(new Date());
        baseProp.setLastFreeLotteryTime(currentTime);

        // 根据权重配置计算 随机获取type_id
        Integer heroTypeId = heroConfig.randHeroTypeId();

        // 创建英雄
        heroService.createHero(playerId, heroTypeId);

        // 返回消息，消息内容为英雄type_id
        SFreeLotteryMsg sFreeLotteryMsg = new SFreeLotteryMsg();
        sFreeLotteryMsg.setHeroTypeId(heroTypeId);
        return SReturnMsg.success(sFreeLotteryMsg);
    }

    // 用户剩余的免费抽奖时间
    private Integer getLastFreeTime(Integer playerId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        // 获取免费抽奖的间隔时间
        Integer freeDistLotteryTime = globalConfig.getIntegerValue(GlobalConfig.FREE_LOTTERY_TIME);
        // 获取用户最后一次免费抽奖的时间
        String lastFreeLotteryTime = playerInfo.getBaseProp().getLastFreeLotteryTime();

        // 时间格式转换时间戳
        SimpleDateFormat df = new SimpleDateFormat(DataUtil.YYYY_MM_DD_HH_MM_SS);
        long lastFreeLotteryUnixTime = 0;
        try {
            lastFreeLotteryUnixTime = df.parse(lastFreeLotteryTime).getTime()/1000;
        }catch (Exception e) {
            log.error("getTavernInfo error: playerId="+playerId + " lastFreeLotteryTime=" + lastFreeLotteryTime);
            throw new TavernFormatErrorException();
        }

        // 当前时间
        Date date = new Date();
        long currentTime = date.getTime()/1000;

        // 用户剩余的免费抽奖时间倒计时
        long lastFreeTime = lastFreeLotteryUnixTime + freeDistLotteryTime - currentTime;
        return (int)lastFreeTime;
    }
}

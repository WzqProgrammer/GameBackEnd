package com.wzqCode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.mapper.PlayerMapper;
import com.wzqCode.obj.db.Player;
import com.wzqCode.obj.msg.HttpStatus;
import com.wzqCode.obj.msg.server.login.SReturnMsg;
import com.wzqCode.obj.msg.server.player.SGetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    PlayerMapper playerMapper;

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

            try {
                playerMapper.insert(player);
            }catch(Exception e){
                //用户信息插入失败
                return SReturnMsg.error(HttpStatus.PLAYER_CREATE_ERROR.getCode(), HttpStatus.PLAYER_CREATE_ERROR.getMsg());
            }
        }

        //向用户返回角色信息
        SGetInfo sGetInfo = new SGetInfo();
        sGetInfo.setNickname(player.getNickname());
        sGetInfo.setCheckpoint(player.getCheckpoint());
        sGetInfo.setCoin(player.getCoin());
        sGetInfo.setHead(player.getHead());
        sGetInfo.setLevel(player.getLevel());

        return SReturnMsg.success(sGetInfo);
    }
}

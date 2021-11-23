package com.wzqCode.obj.cache;

import com.wzqCode.obj.db.Hero;
import com.wzqCode.obj.db.Player;
import lombok.Data;
import java.util.List;

/**
 * @author 14188
 */
@Data
public class PlayerInfo {

    //用户基础信息
    private Player baseProp;

    //英雄列表
    private List<Hero> heroes;

    // 最后一次的心跳时间
    private Long lastHeartBeatTime;
}

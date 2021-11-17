package com.wzqCode.obj.msg.server.hero;

import com.wzqCode.obj.db.Hero;
import lombok.Data;

import java.util.List;

/**
 * @author 14188
 * @date 2021/11/17 13 :58
 * @description
 */

@Data
public class SGetHeroListMsg {

    List<Hero> heroList;
}

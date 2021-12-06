package com.wzqCode.config.gameConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.mapper.HeroModuleMapper;
import com.wzqCode.obj.module.HeroModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class HeroConfig implements CommandLineRunner {

    @Autowired
    HeroModuleMapper heroModuleMapper;

    // 英雄模板列表
    private ArrayList<HeroModule> heroModuleList = new ArrayList<>();

    public ArrayList<HeroModule> getAllHeroModuleList(){
        return heroModuleList;
    }

    // 当前的总权重
    private Integer allWeight = 0;

    public HeroModule getHeroModuleById(Integer typeId){
        for (HeroModule heroModule : heroModuleList) {
            if(heroModule.getTypeId().equals(typeId))
                return heroModule;
        }
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        // 加载配置 根据需求增加配置到heroModuleList
        QueryWrapper<HeroModule> wrapper = new QueryWrapper<>();
        List<HeroModule> list = heroModuleMapper.selectList(wrapper);
        this.heroModuleList.addAll(list);
        for (HeroModule heroModule : heroModuleList) {
            allWeight += heroModule.getLotteryWeight();
        }
    }

    // 基于权重随机抽取英雄type_id
    public Integer randHeroTypeId(){

        Random random = new Random();
        // 生成 0~allWeight-1 的随机值
        Integer randWeight = random.nextInt(allWeight);

        Integer heroTypeId = 0;
        Integer tempWeight = 0;

        // 再次遍历所有英雄模板，根据随机权重值，获取此范围内的英雄模板id
        for (HeroModule heroModule : heroModuleList) {
            Integer weight = heroModule.getLotteryWeight();
            if(randWeight >= tempWeight && randWeight < tempWeight + weight){
                heroTypeId = heroModule.getTypeId();
                break;
            }
            tempWeight += weight;
        }
        return heroTypeId;
    }
}

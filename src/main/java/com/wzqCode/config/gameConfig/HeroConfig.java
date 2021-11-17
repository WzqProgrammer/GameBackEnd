package com.wzqCode.config.gameConfig;

import com.wzqCode.obj.module.HeroModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class HeroConfig implements CommandLineRunner {

    // 英雄模板列表
    private ArrayList<HeroModule> heroModuleList = new ArrayList<>();

    // 英雄每星级可提升的等级数
    private Integer starLv;
    //英雄最大星级
    private Integer maxStar;

    public Integer getStarLv(){
        return starLv;
    }

    public Integer getMaxStar(){
        return maxStar;
    }

    public ArrayList<HeroModule> getAllHeroModuleList(){
        return heroModuleList;
    }

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
        HeroModule heroModule1 = new HeroModule();
        heroModule1.setTypeId(1);
        heroModule1.setName("齐天大圣孙悟空");
        heroModule1.setAtt(100);
        heroModule1.setDef(20);
        heroModule1.setHp(1000);
        heroModule1.setLvUpAtt(10);
        heroModule1.setLvUpDef(2);
        heroModule1.setLvUpHp(100);

        HeroModule heroModule2 = new HeroModule();
        heroModule2.setTypeId(2);
        heroModule2.setName("野猪精");
        heroModule2.setAtt(80);
        heroModule2.setDef(16);
        heroModule2.setHp(800);
        heroModule2.setLvUpAtt(8);
        heroModule2.setLvUpDef(2);
        heroModule2.setLvUpHp(120);

        heroModuleList.add(heroModule1);
        heroModuleList.add(heroModule2);

        starLv = 20;
        maxStar = 5;
    }
}

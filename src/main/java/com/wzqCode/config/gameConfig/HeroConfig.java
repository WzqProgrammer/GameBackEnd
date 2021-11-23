package com.wzqCode.config.gameConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.mapper.HeroModuleMapper;
import com.wzqCode.obj.module.HeroModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class HeroConfig implements CommandLineRunner {

    @Autowired
    HeroModuleMapper heroModuleMapper;

    // 英雄模板列表
    private ArrayList<HeroModule> heroModuleList = new ArrayList<>();


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
        QueryWrapper<HeroModule> wrapper = new QueryWrapper<>();
        List<HeroModule> list = heroModuleMapper.selectList(wrapper);
        this.heroModuleList.addAll(list);
    }
}

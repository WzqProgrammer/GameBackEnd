package com.wzqCode.config.gameConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.mapper.GlobalDataMapper;
import com.wzqCode.obj.db.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalConfig implements CommandLineRunner {

    @Autowired
    GlobalDataMapper globalDataMapper;

    private ConcurrentHashMap<String, String> globalDataMap = new ConcurrentHashMap<>();

    public static final String STAR_LV = "star_lv";
    public static final String MAX_STAR = "max_star";

    public String getStringValue(String key){
        return globalDataMap.get(key);
    }

    public Integer getIntegerValue(String key){
        return Integer.parseInt(globalDataMap.get(key));
    }

    @Override
    public void run(String... args) throws Exception {
        QueryWrapper<GlobalData> wrapper = new QueryWrapper<>();
        List<GlobalData> globalDataList = globalDataMapper.selectList(wrapper);
        for (GlobalData globalData : globalDataList) {
            globalDataMap.put(globalData.getKeyName(), globalData.getValueName());
        }
    }
}

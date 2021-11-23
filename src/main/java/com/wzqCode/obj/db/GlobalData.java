package com.wzqCode.obj.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("global_data")
public class GlobalData {
    private Integer id;

    private String keyName;

    private String valueName;

    private String description;
}

package com.wzqCode.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.wzqCode.mapper")
public class MybatisPlusConfig {

}

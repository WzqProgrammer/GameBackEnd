package com.wzqCode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author 14188
 * @date 2021/10/10 11 :10
 * @description 日志组件swagger的配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket groupDocket(Environment e){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                //指定swagger扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.wzqCode.controller"))
                .build();
    }

    private ApiInfo getApiInfo() {
        //设置作者信息
        Contact contact = new Contact("wzq", "www.baidu.com", "xxx@qq.com");
        //设置文档信息
        ApiInfo apiInfo = new ApiInfo(
                "游戏后端服务器接口",
                "游戏后端服务器接口描述",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());
        return apiInfo;

    }
}

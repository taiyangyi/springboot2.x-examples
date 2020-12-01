package com.daobili.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author bamaw
 * @Date 2020-11-23  15:56
 * @Description Swagger配置类
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {



    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                // 分组名,不指定默认为default
                .groupName("业务数据模块API")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.daobili.controller"))
                .paths(PathSelectors.any())
                .build()
                // 设置swagger-ui.html页面上的一些元素信息
                .apiInfo(apiInfo());
    }


    /**
     * 详细信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 业务数据接收器RESTful API
                .title("SpringBoot整合Swagger")
                // 提供业务数据接收模块/业务数据处理模块的文档
                .description("SpringBoot整合Swagger，详细信息......")
                .termsOfServiceUrl("http://127.0.0.1:8080/")
                .version("1.0")
                .contact(new Contact("巴莫", "http://www.taiyangyi.com","tbamaw@163.com"))
                .license("The Apache License")
                .licenseUrl("http://www.baidu.com")
                .build();
    }

}

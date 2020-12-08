package com.daobili.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
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
 * @Date 2020-11-23  23:19
 * @Description Swagger配置类 随环境变化启用Swagger
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig2 {

    /**
     * 配置Swagger Bean实例
     * @return
     */
    @Bean
    public Docket createRestApi(Environment env) {

        // 获取项目环境
        // 设置要显示的Swagger环境
        // 通过springboot环境，判断是否处在自己设定的环境
        Profiles of = Profiles.of("dev","test");
        boolean flag = env.acceptsProfiles(of);
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否启用Swagger
                .enable(flag)
                // 配置扫描接口及API
                .select()
                // 配置要扫描借口的方式
                    // basePackage:指定扫描的包 com.xxx.xxx
                    // any: 全扫描
                    // none: 不扫描
                    // withClassAnnotation: 扫描类上的注解 (传递注解class,表示只扫描有该注解的类)
                    // withMethodAnnotation: 扫描方法上的注解
                    // 扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.daobili.controller"))
                // 过滤什么路径
                    // ant: .ant("/api/**") 表示只扫描/api/**开头的的接口
                    // any: 过滤全部的
                    // none: 不过滤全部的
                    // regex: 正则
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置Swagger详细信息 ApiInfo
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("SpringBoot整合Swagger")
                // 描述
                .description("SpringBoot整合Swagger，详细信息......")
                // 版本号
                .version("1.0")
                // 作者信息
                .contact(new Contact("巴莫", "http://www.taiyangyi.com","tbamaw@163.com"))
                .build();
    }

}

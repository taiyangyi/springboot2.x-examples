package com.daobili.config.third;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
 * @Date 2020-12-08  22:22
 * @Description Swagger配置类
 */
@Configuration
@EnableSwagger2
@Profile({"dev","qa"})
@EnableConfigurationProperties(SwaggerInfo.class)
public class SwaggerConfig3 {

    @Autowired
    private SwaggerInfo swaggerInfo;


    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfo.getGroupName())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }


    /**
     * 设置api信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .description(swaggerInfo.getDescription())
                .contact(new Contact(swaggerInfo.getAuthor(), swaggerInfo.getAuthorUrl(), swaggerInfo.getAuthorEmail()))
                .version(swaggerInfo.getVersion())
                .build();
    }

}

package com.daobili;

import com.daobili.config.DruidConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * SpringBoot + SpringMVC + SpringCore + MyBatis + Druid整合案例
 * @Author bamaw
 * @Date 2021-03-31  22:15
 * @Description
 *
 * 使用@Import就可以将其他的配置管理类导入进来
 */
@SpringBootApplication
@Import(DruidConfig.class)
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }

}

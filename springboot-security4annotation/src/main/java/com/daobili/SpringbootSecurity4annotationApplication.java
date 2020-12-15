package com.daobili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


/**
 * 
 * @Author bamaw 
 * @Date 2020-12-05  15:59
 * @Description 
 */

@MapperScan("com.daobili.mapper")
@SpringBootApplication
/**
 * 这个注解添加至配置类或者启动类都可以
 */
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringbootSecurity4annotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity4annotationApplication.class, args);
    }

}

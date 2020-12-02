package com.daobili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author bamaw
 */

@SpringBootApplication
@MapperScan("com.daobili.mapper")
public class SpringbootSecurity4dbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity4dbApplication.class, args);
    }

}

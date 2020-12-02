package com.daobili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.daobili.mapper")
@SpringBootApplication
public class SpringbootSecurity4dbLogin4htmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity4dbLogin4htmlApplication.class, args);
    }

}

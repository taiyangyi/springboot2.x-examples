package com.daobili.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author bamaw
 * @Date 2020-11-30  23:00
 * @Description security配置类
 * WebSecurityConfigurerAdapter
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        // 设置登录的用户名和密码
        auth.inMemoryAuthentication()
                .withUser("daobili")
                .password(encoder.encode("123321"))
                .roles("admin");
    }


    @Bean
    PasswordEncoder buildPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }
}

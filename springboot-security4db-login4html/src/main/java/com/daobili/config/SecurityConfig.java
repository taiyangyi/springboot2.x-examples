package com.daobili.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author bamaw
 * @Date 2020-12-03  00:40
 * @Description 基于 自定义编写实现类 UserDetailService
 * 第一步操作
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(buildPasswordEncoder());
    }

    @Bean
    PasswordEncoder buildPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义自己编写的登录页面
        http.formLogin()
                // 登录页面设置
                .loginPage("/login.html")
                // 登录访问的路径
                .loginProcessingUrl("/user/login")
                // 登录成功之后跳转的路径
                .defaultSuccessUrl("/success").permitAll()
                // 定义访问(页面是否通过认证访问区别：不需要认证以及需要认证的页面)
                .and().authorizeRequests()
                    // 设置下面的路径可以直接访问，不需要通过认证
                    .antMatchers("/","/hello","/user/login").permitAll()
                // 表示其他请求 需要认证
                .anyRequest().authenticated()
                // 关闭csrf防护
                .and().csrf().disable();
    }
}

package com.daobili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *
 * @Author bamaw 
 * @Date 2021-03-30  21:26
 * @Description
 *
 * @EnableAutoConfiguration(点击@SpringBootApplication查看其注解里面的东西)
 * AutoConfiguration
 * 是springboot最重要的核心功能之一，
 * 我们都知道springboot核心思想就是不要去做太多配置，全部基于约定的一些规则，自动完成一些配置。
 * 比如：我们项目引入了 spring-boot-starter-web 依赖，那么它就会根据我们开发web程序的特点，
 * 自动完成tomcat服务器等相关的web配置
 *
 *
 * 梳理SpringBoot启动的过程：
 * 1.AutoConfiguration完成所有的配置：如：spring mvc、spring、tomcat
 * 2.将我们的工程部署到内嵌的tomcat容器中
 * 3.接着启动tomcat容器之后，然后初始化spring的核心容器，是跟spring mvc整合在一起
 * 4.spring的核心容器就自动去扫描所有的包，有没有带@RestController之类的注解，如果存在，那么将这个controller初始化
 * 5.将我们@RestController注解的类 实例化成一个bean,注入到自己的spring容器中
 * 6.此时spring mvc的核心servlet对外提供接收http请求，接收到http请求之后，就会将请求转发至对应的controller bean
 * 7.controller bean处理完请求之后，spring mvc将请求结果返回给浏览器
 *
 */
@SpringBootApplication
public class SpringbootQuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootQuickstartApplication.class, args);
    }

}

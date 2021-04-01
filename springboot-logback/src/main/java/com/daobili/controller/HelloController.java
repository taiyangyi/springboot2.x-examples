package com.daobili.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bamaw
 * @date 2021-04-01  18:46
 *
 * 两种方式使用logback
 * 1.通过创建LoggerFactory实例
 * 2.通过注解 @Slf4j 的方式
 */
@RestController
@RequestMapping("/hello")
// 通过注解 @Slf4j 的方式
@Slf4j
public class HelloController {

    /**
     * 通过创建LoggerFactory实例
     */
    //private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/say/{name}")
    public String sayHello(@PathVariable("name") String name) {
        //LOGGER.info("hello,{}",name);
        log.info("使用注解的方式日志服务：sayHello:{}" + name);
        return "hello" + name;
    }
}

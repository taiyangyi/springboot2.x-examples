package com.daobili.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author bamaw
 * @Date 2020-12-03  23:33
 * @Description
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello security!";
    }


    @GetMapping("/success")
    public String success() {
        return "登录成功后，跳转到成功页面!";
    }

}

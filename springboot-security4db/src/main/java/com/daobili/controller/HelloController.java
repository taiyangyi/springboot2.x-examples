package com.daobili.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author bamaw
 * @Date 2020-12-02  23:33
 * @Description
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello security!";
    }

}

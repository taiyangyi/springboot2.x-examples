package com.daobili.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author bamaw
 * @Date 2020-11-30  22:52
 * @Description
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello security!";
    }

}

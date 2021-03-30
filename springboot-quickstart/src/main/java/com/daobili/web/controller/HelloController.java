package com.daobili.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bamaw
 * @date 2021-03-30  21:35
 *
 * @RestController 意思就是仅仅提供Restful接口，返回结果给浏览器，不会走传统的渲染模板视图页面
 */
@RestController
public class HelloController {

    @RequestMapping("/index")
    public String index() {
        return "Hello World";
    }

    /**
     * @PathVariable注解会将{name}代表的值，注入方法入参
     *
     * @param name
     * @return String
     */
    @RequestMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "hello," + name;
    }

}

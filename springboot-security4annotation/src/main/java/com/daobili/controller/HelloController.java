package com.daobili.controller;

import com.daobili.domain.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author bamaw
 * @Date 2020-12-05  16:05
 * @Description
 */
@RestController
public class HelloController {

    @GetMapping("/ ")
    public String hello() {
        return "hello security!";
    }


    @GetMapping("/success")
    public String success() {
        return "登录成功后，跳转到成功页面!";
    }

    @GetMapping("/getAnnotation")
    @Secured({"ROLE_wuye","ROLE_admin"})
    public String getAnnotation() {
        return "hello annotation";
    }


    /**
     * 进入方法前验证
     * @return
     */
    @RequestMapping("/preAuthorize")
    //@PreAuthorize("hasRole('ROLE_管理员')")
    @PreAuthorize("hasAnyAuthority('admins')")
    public String preAuthorize(){
        System.out.println("preAuthorize");
        return "preAuthorize";
    }


    /**
     * 方法执行后，返回的是否进行校验
     * @return
     */
    @RequestMapping("/testPostAuthorize")
    @PostAuthorize("hasAnyAuthority('admins')")
    public String postAuthorize(){
        System.out.println("test--PostAuthorize"); return "PostAuthorize";
    }


    /**
     * 权限验证之后对数据进行过滤 留下用户名是 admin1 的数据
     * @return
     */
    @GetMapping("getAll")
    @PreAuthorize("hasRole('ROLE_admin')")
    @PostFilter("filterObject.username == 'admin1'")
    @ResponseBody
    public List<User> getAllUser(){
        List<User> list = new ArrayList<>();
        list.add(new User(10,"admin1","bamaw"));
        list.add(new User(11,"admin2","daobili"));
        return list;
    }


    /**
     * 进入控制器之前对数据进行过滤
     * @param list
     * @return
     */
    @GetMapping("getTestPreFilter")
    @PreAuthorize("hasRole('ROLE_admin')")
    @PreFilter(value = "filterObject.id%2==0") @ResponseBody
    public List<User> getTestPreFilter(@RequestBody List<User> list){
        list.forEach(t-> {
            System.out.println(t.getId()+"\t"+t.getUsername());
        });
        return list;
    }


}

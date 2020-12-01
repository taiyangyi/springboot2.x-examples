package com.daobili.controller;

import com.daobili.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author bamaw
 * @Date 2020-11-23  16:24
 * @Description 用户控制器接口
 */
@Api("用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("根据id查询用户")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "99")
    @GetMapping("/")
    public User getUserById(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }


    @ApiOperation("根据id更新用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "99"),
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "bamaw")
    })
    public User updateUsernameById(String username, Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }


    @ApiOperation("根据id更新用户信息")
    @PutMapping("/{id}")
    public User updateUserById(@RequestBody User user) {
        return user;
    }

    @ApiOperation("添加用户")
    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return user;
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {

    }

    @GetMapping("/hello")
    public String hello(String name) {
        return "hello " + name;
    }

}

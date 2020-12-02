package com.daobili.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daobili.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author bamaw
 * @Date 2020-12-02  23:48
 * @Description
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 调用UserMapper方法查询数据库
        // 封装条件/条件构造器
        QueryWrapper<com.daobili.domain.User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        com.daobili.domain.User user = userMapper.selectOne(wrapper);
        // 判断
        // 数据库无此用户，认证失败
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        // 从查询数据库返回user对象，得到用户名和密码，返回
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
}

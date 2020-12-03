## web权限方案-基于角色或权限进行访问控制

###1. hasAuthority 方法
如果当前的主体具有指定的权限，则返回 true,否则返回 false

* 在当前配置类设置访问地址有哪些权限
```java
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

                 // --------------修改点1---------------
                // 表示当前登录用户，只有具有admin权限才可以访问这个路径
                .antMatchers("/hello").hasAuthority("admins")
                
                
                // 表示其他请求 需要认证
                .anyRequest().authenticated()
                // 关闭csrf防护
                .and().csrf().disable();
    }
```

* 在MyUserDetailsService，把返回User对象设置权限

```java
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
        
        // -------------修改点2------------------
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admins");
        
        
        // 从查询数据库返回user对象，得到用户名和密码，返回
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
```
> 测试

1. 修改   `修改点2`为 manager (随便定义)，浏览器访问 返回403，没有权限访问
2. 修改  `修改点2`为 admins (随便定义)，浏览器访问 访问成功

###2. hasAnyAuthority 方法
如果当前的主体有任何提供的角色（给定的作为一个逗号分割的字符串列表）的话，则返回 true,否则返回 false

* 在当前配置类设置访问地址有哪些权限
```java
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

                       
                // 方法一：表示当前登录用户，只有具有admin权限才可以访问这个路径
                //.antMatchers("/hello").hasAuthority("admins")

                // 方法二：多权限
                .antMatchers("/hello").hasAnyAuthority("admins,manager")
                
                // 表示其他请求 需要认证
                .anyRequest().authenticated()
                // 关闭csrf防护
                .and().csrf().disable();
    }
```

* 在MyUserDetailsService，把返回User对象设置权限

```java
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
        
        // -------------修改点2------------------
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("manager");
        
        
        // 从查询数据库返回user对象，得到用户名和密码，返回
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
```
> 测试

1. 修改   修改点2为 manager (随便定义)，浏览器访问 访问成功
2. 修改   修改点2为 admins (随便定义)，浏览器访问 访问成功


###3. hasRole 方法
如果用户具备给定角色就允许访问，否则出现403
如果当前主体具有指定的角色，则返回true

源码赏析：
```java
private static String hasRole(String role) {
    Assert.notNull(role, "role cannot be null");
    Assert.isTrue(!role.startsWith("ROLE_"), () -> {
        return "role should not start with 'ROLE_' since it is automatically inserted. Got '" + role + "'";
    });
    return "hasRole('ROLE_" + role + "')";
}
```

* 在当前配置类设置访问地址有哪些权限
注意配置文件中不需要添加”ROLE_“，因为上述的底层代码会自动添加与之进行匹配。
```java
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

            // 方法一：表示当前登录用户，只有具有admin权限才可以访问这个路径
            //.antMatchers("/hello").hasAuthority("admins")

            // 方法二：多权限
            //.antMatchers("/hello").hasAnyAuthority("admins,manager")

            // 方法三：hasRole
            .antMatchers("/hello").hasRole("wuye")

            // 表示其他请求 需要认证
            .anyRequest().authenticated()
            // 关闭csrf防护
            .and().csrf().disable();
}
```

* 在MyUserDetailsService，把返回User对象设置权限

```java
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

    // 方法一：正常访问
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admins");
    // 方法一：拒绝访问 403
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("akaka");

    // 方法二:正常访问
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("manager");

    // 方法三：前缀处理
    List<GrantedAuthority> auths =
            AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_wuye");

    // 从查询数据库返回user对象，得到用户名和密码，返回
    return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
}
```

###4. hasAnyRole 方法
表示用户具备任何一个条件都可以访问。

```java
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

            // 方法一：表示当前登录用户，只有具有admin权限才可以访问这个路径
            //.antMatchers("/hello").hasAuthority("admins")

            // 方法二：多权限
            //.antMatchers("/hello").hasAnyAuthority("admins,manager")

            // 方法三：hasRole
            //.antMatchers("/hello").hasRole("wuye")


            // 方法四：
            .antMatchers("/hello").hasAnyRole("wuye,admin,role")

            // 表示其他请求 需要认证
            .anyRequest().authenticated()
            // 关闭csrf防护
            .and().csrf().disable();
}
```

* 在MyUserDetailsService，把返回User对象设置权限

```java
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

    // 方法一：正常访问
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admins");
    // 方法一：拒绝访问 403
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("akaka");

    // 方法二:正常访问
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("manager");

    // 方法三：前缀处理
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_wuye");

    // 方法四：前缀处理 多个角色
    List<GrantedAuthority> auths =
            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_wuye,ROLE_admin,ROLE_role");


    // 从查询数据库返回user对象，得到用户名和密码，返回
    return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
}
```

# web权限方案-认证-自定义登录页面

* 自定义设置登录页面
* 不需要认证可以访问

### 1.引入相关依赖
```xml
<!--security-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!--mysql-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<!--mybatis-plus-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
```

### 2.创建数据库和数据库表

```sql
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
```

### 3.创建user表对应实体类

```java
public class User {

    private Integer id;
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

### 4.整合mybatis-plus，创建接口，继承mybatis-plus的接口
```java
@Repository
public interface UserMapper extends BaseMapper<User> {
}
```

### 5.编写配置类放行登录页面以及静态资源
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(buildPasswordEncoder());
    }

    @Bean
    PasswordEncoder buildPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


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
                    // 设置下面的路径可以直接访问，不需要通过认证
                    .antMatchers("/","/hello","/user/login").permitAll()
                // 表示其他请求 需要认证
                .anyRequest().authenticated()
                // 关闭csrf防护
                .and().csrf().disable();
    }
}
```


### 6.在MyUserDetailsService调用mapper里面的方法-查询数据库进行用户认证
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
    List<GrantedAuthority> auths =
            AuthorityUtils.commaSeparatedStringToAuthorityList("role");
    // 从查询数据库返回user对象，得到用户名和密码，返回
    return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
}
```

### 7.在启动类添加注解 MapperScan
```java
@SpringBootApplication
@MapperScan("com.daobili.mapper")
public class SpringbootSecurity4dbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity4dbApplication.class, args);
    }

}
```

### 8.配置数据库信息

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/db_security?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=123321
```

### 9.引入登录界面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录界面</title>
</head>
<body>
    <form action="/user/login" method="post">
        用户名：<input type="text" name="username">
        <br/>
        密码：<input type="text" name="password">
        <br/>
        <input type="submit" value="login">
    </form>
</body>
</html>
```
> 注意:页面提交方式必须为 post 请求，所以上面的页面不能使用，用户名，密码必须为 username,password

*原因：*
在执行登录的时候会走一个过滤器 UsernamePasswordAuthenticationFilter
```java
public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");
    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private boolean postOnly = true;

    public UsernamePasswordAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            username = username != null ? username : "";
            username = username.trim();
            String password = this.obtainPassword(request);
            password = password != null ? password : "";
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
```

### 10.编写控制器

```java
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
```




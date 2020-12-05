# web权限方案-查询数据库完成用户认证

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

### 5.配置security配置类 securityconfig
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





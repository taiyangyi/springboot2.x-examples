## web权限方案
* 认证
* 授权

### 1.设置登录的用户名和密码
* 方式一：通过配置文件；
```properties
## security
spring.security.user.name=bamaw
spring.security.user.password=123321
```

* 方式二：通过配置类；
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        // 设置登录的用户名和密码
        auth.inMemoryAuthentication()
                .withUser("daobili")
                .password(encoder.encode("123321"))
                .roles("admin");
    }


    @Bean
    PasswordEncoder buildPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
```

* 方式三：自定义编写实现类 UserDetailService
> 实现步骤：
1. 创建配置类，设置使用哪个userDetailsService实现类

```java
/**
 * @Author bamaw
 * @Date 2020-12-02  00:20
 * @Description 基于 自定义编写实现类 UserDetailService
 * 第一步操作
 */
@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

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

2. 编写实现类，返回User对象，User对象有用户名和密码以及操作权限

```java
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        return new User("daobili", new BCryptPasswordEncoder().encode("123321"), auths);
    }
}
```



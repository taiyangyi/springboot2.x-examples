## web权限方案-认证授权注解使用+用户注销操作+记住我功能开发

在使用注解之前要开启注解功能
使用位置：这个注解添加至配置类或者启动类都可以

> @EnableGlobalMethodSecurity(securedEnabled = true)


```java
@MapperScan("com.daobili.mapper")
@SpringBootApplication
/**
 * 这个注解添加至配置类或者启动类都可以
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringbootSecurity4annotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurity4annotationApplication.class, args);
    }

}
```


> @Secured

判断用户是否具有角色，另外需要注意的是这里匹配的字符串需要添加前缀“ROLE_“。

在Controller方法中设置角色
```java
@GetMapping("/getannot")
@Secured({"ROLE_wuye","ROLE_admin"})
public String getAnnotation() {
    return "hello annotation";
}
```
如果要访问ok,需要在MyUserDetailsService设置角色
```java
 List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_wuye,ROLE_admin,ROLE_role");

```

> @PreAuthorize

先开启注解功能:
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize:注解适合进入方法前的权限验证， @PreAuthorize 可以将登录用
户的 roles/permissions 参数传到方法中。
```java
/**
 * 进入方法前验证
 * @return
 */
@RequestMapping("/preAuthorize")
@ResponseBody
//@PreAuthorize("hasRole('ROLE_管理员')")
@PreAuthorize("hasAnyAuthority('admins')")
public String preAuthorize(){
    System.out.println("preAuthorize");
    return "preAuthorize";
}
```

> @PostAuthorize

先开启注解功能:
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PostAuthorize 注解使用并不多，在方法执行后再进行权限验证，适合验证带有返回值 的权限。
```java
/**
 * 方法执行后，返回的是否进行校验
 * @return
 */
@RequestMapping("/testPostAuthorize")
@PostAuthorize("hasAnyAuthority('admins')")
public String postAuthorize(){
    System.out.println("test--PostAuthorize"); return "PostAuthorize";
}
```

> @PostFilter

@PostFilter :权限验证之后对数据进行过滤 留下用户名是 admin1 的数据 
表达式中的 filterObject 引用的是方法返回值 List 中的某一个元素
```java
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
```

> @PreFilter

@PreFilter: 进入控制器之前对数据进行过滤

```java
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
```

### 用户注销操作
在配置类中添加退出映射地址
```java
// 用户注销操作
http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();
```

> 测试验证

1. 修改配置类，登录成功后跳转到成功页面；
2. 在成功页面添加超链接，写设置退出路径；
3. 登录成功后，在成功页面点击退出，再去访问其他Controller控制器接口，不能进行访问


### 记住我功能开发(基于数据库)

#### 1.创建表

```sql
CREATE TABLE `persistent_logins` (
`username` varchar(64) NOT NULL,
`series` varchar(64) NOT NULL,
`token` varchar(64) NOT NULL,
`last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
#### 2.添加数据库配置文件

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/db_security?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=123321

```

#### 3.配置类-记住我功能

```java
@Autowired
private DataSource dataSource;
 
 
 @Bean
 public PersistentTokenRepository persistentTokenRepository() {
     JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
 
     jdbcTokenRepository.setDataSource(dataSource);
 
     // 自动创建表，第一次执行创建，以后要执行就要删除掉
     //jdbcTokenRepository.setCreateTableOnStartup(true);
     return jdbcTokenRepository;
 }
 
 
 // 开启记住我功能
http.rememberMe()
 .tokenRepository(persistentTokenRepository())
 // 设置有效时长 单位是秒
 // 默认 2 周时间。但是可以通过设置状态有效时间，即使项目重新启动下次也可以正常登录。
 .tokenValiditySeconds(60)
 .userDetailsService(userDetailsService)
```


#### 4.登录页面增加记住我选择项
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
        <input type="checkbox" name="remember-me"/>自动登录
        <br/>
        <input type="submit" value="login">
    </form>
</body>
</html>
```
此处:name 属性值必须位  `remember-me`.不能改为其他值
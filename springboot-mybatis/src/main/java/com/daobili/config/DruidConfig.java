package com.daobili.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid连接池配置管理类
 *
 * @author bamaw
 * @date 2021-03-31  22:30
 *
 *
 *
 * 拓展：
 * 我们现在要配置一个数据源，是基于开源的数据库连接池的组件来配置，如：c3p0、druid.
 * 在没有springboot出现之前，我们使用传统的spring整合的方式，必然是在applicationContext.xml中去配置一个bean,
 * datasource所有的配置都是放在applicationContext.xml中的
 *
 * 但是，现在springboot的核心思想是将全部的配置放在 application.properties中用于集中管理所有配置，同时将以前
 * xml中的配置bean的形式通过java代码的注解形式配置bean
 *
 * 使用@Configuration标注，表示这是一个配置管理类,在这个类中可以将外部的application.properties中需要的配置加载进来，
 * 使用@Value注解即可加载外部配置
 *
 */
@Configuration
public class DruidConfig {

    /**
     * 因为spring boot是默认开启了资源过滤的
     * 所以这里的配置，都会自动从application.properties配置文件中加载出来，设置到这个@Configuration类中
     */

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;


    /**
     * 在这个配置类中，直接基于配置信息，创建出了一个bean,这个bean就是一个DataSource,
     * 这个DataSource bean就会被纳入spring容器的管理范围之内
     * @return
     */

    /**
     * @Bean
     * 当我们手头拥有所有的配置信息之后，就可以使用@Bean注解 + 方法的形式，在方法中基于加载进来的配置参数来
     * 创建你需要的各种bean,并将其返回，这个bean就会被注册到spring容器中去；
     *
     * @Primary
     * 比如我们返回的datasource类型的bean,spring在进行bean装配的时候，比如有其他某个类用@Autowired来要求
     * 装配一个Datasource bean,但是此时如果你的应用中配置了多个Datasource类型的bean,那么就会装配失败
     * 所以在这个bean中标注一个@Primary 那么在出资按多个同一类型的bean的时候，就会选择加了@primary的bean
     *
     */
    @Bean
    @Primary
    public DataSource dataSource(){
        // 这里就是用外部加载进来的配置信息，创建出来一个Druid连接池
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

}

#### mybatis-spring-boot-starter的工作原理如下
1. 自动发现一个注册好的DataSource：applicationContext.xml手动配置，不用了 -> @Configuration作用替代掉以前的xml配置文件
2. 自动创建一个SqlSessionFactory，并且将DataSource传入SqlSessionFactory中
3. 自动基于SqlSessionFactory创建一个SqlSessionTemplate
4. 扫描所有的Mapper，将SqlSessionTemplate注入其中，然后将Mapper注册到Spring容器上下文中


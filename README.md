# 基于SSM的商城系统

 ### 核心技术

- 核心框架：Spring-4.2.4
- 持久层框架：MyBatis-3.2.8
- 数据库连接池：Alibaba Druid-1.0.9
- 数据库：MySQL-8.0.16
- 逆向工程：mybatis-generator
- 版本控制：Git
- 编程语言：Java1.8
- IDE：Intellij IDEA
- 项目构建：Maven

### 后台工程

e3-parent：父工程，依赖包版本管理

​	|--e3-common：通用的pojo类

​	|--e3-manager：MVC三层框架

​		|--e3-manager-dao：dao层

​		|--e3-manager-pojo：数据库表对应的pojo

​		|--e3-manager-interface：service层

​		|--e3-manager-service：service层

​    	|--e3-manager-web：controller层





### 遇到的问题

##### 1、web工程web.xml放置

在IDEA中创建maven空项目时，目录结构基本是`src/main/java`和`src/main/resources`，这里并不会创建webapp文件夹。如果将webapp文件夹放置在resources文件夹下，运行工程就会报错。因此，我们需要创建`src/main/webapp/WEB-INF/web.xml`，这样便能运行成功。

##### 2、mapper.java文件与mapper.xml文件放置同一个文件夹下问题

在maven工程中，如果mapper.java文件与mapper.xml文件放置在同一个文件夹下，编译后两者不会发布到同一个target文件夹下的，mapper.java编译后形成class文件在target文件夹下，而mapper.xml文件将会发布到resources文件夹下。这样mapper.xml文件便不能与mapper.java文件绑定，即会报错。

解决办法：在pom文件中添加如下代码。下面代码表示`src/main/java`文件夹下的`**/*.properties`和`**/*.xml`文件都会被发布到target文件夹下。

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

##### 3、Dubbo工程debug

设置`timeout`属性，dubbo默认1s内没有查询到服务将抛异常，设置timeout能够延迟查询时间

```xml
<!-- 使用dubbo发布服务 -->
<!-- 提供方应用信息，用于计算依赖关系 -->
<dubbo:application name="onlinemall-manager-service" />
<dubbo:registry protocol="zookeeper"
                address="218.197.194.82:2181" />
<!-- 用dubbo协议在20880端口暴露服务 -->
<dubbo:protocol name="dubbo" port="30110" />
<!-- 声明需要暴露的服务接口 -->
<dubbo:service interface="com.xwt.onlinemall.service.ItemService" timeout="600000"
               ref="itemServiceImpl" />
```




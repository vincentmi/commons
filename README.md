# commons

>
> Author : miwenshu@gmail.com
>

包含微服务的一些工具,包含权限验证框架
返回格式化工具

## 安装

加入父级依赖避免冲突

```xml
<parent>
    <groupId>com.tourscool</groupId>
    <artifactId>parent</artifactId>
    <version>1.0.1</version>
</parent>
```

加入```commons```库

```xml
<dependency>
    <groupId>com.vnzmi.commons</groupId>
    <artifactId>micro-service-utils</artifactId>
    <version>1.0.3</version>
</dependency>
```

**注意**

需要加入公司的私有库来下周上面的Jar包

```xml
<repositories>
    <repository>
        <id>tourcool</id>
        <name>tourscool</name>
        <url>http://nexus.tourscool.cn/repository/tourscool/</url>
        <layout>default</layout>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>
```

## 初始化应用程序

```java 
package com.tourscool.passport;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@ComponentScan("com.vnzmi.commons")
@ComponentScan("com.tourscool.passport")
@ServletComponentScan
@EnableJpaRepositories(repositoryBaseClass = PhiloRepositoryImpl.class)
@EnableWebMvc
@EnableConfigurationProperties
@EnableFeignClients(basePackages = {"com.tourscool.passport"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }

    @Configuration
    public static class WebConfig extends WebMvcConfigurationSupport {
        @Override
        protected void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor((HandlerInterceptor) new AuthInterceptor());
        }
    }
}
```

>
> 根据实际情况启用相关的cloud组件
> 注意添加 ```@ComponentScan("com.vnzmi.commons")``` 来启用一些已经创建好的配置
> 如果需要自己来进行配置 ```excludeFilters```掉 ```com.vnzmi.commons.setup```
> ```AuthInterceptor``` 拦截器用于进行权限检查.如果不需要可以不添加
>

## 使用权限控制库

依赖 authorize 和 passport服务,相关配置如下

```yaml
app:
  authorize:
    url: http://authorize.dev.philo.in/
    app-id: 1
    name: authorize
  passport:
    url: http://passport.dev.philo.in/
    app-id: 1
    name: passport
```

#### 确保添加了拦截器

```java
 @Configuration
    public static class WebConfig extends WebMvcConfigurationSupport {
        @Override
        protected void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor((HandlerInterceptor) new AuthInterceptor());
        }
    }
```

#### 在你需要进行权限控制的控制器添加注解

```java
@RestController
public class Endpoint {

    @GetMapping("/info")
    @Authorize("profile")
    public HashMap<String,Object> getUserInfo()
    {
        return new HashMap<String,Object>(){{
            put("uid", Auth.id());
            put("t",new Date());
        }};
    }
}
```

```@Authorize("profile")``` 表示这个控制器需要拥有 ```profile```权限的用户才可以访问.```@Authorize({"profile","order_list""})```表示访问该接口需要具备以上两个权限.
```@Authorize```则表示该控制器方法需要用户已经登入系统.

```@Passport``` 注解用法一样.只是权限的判定交由```passport``` 系统进行.这个系统是作为用户的单点登录系统.会进行较为简单的权限判定.

**silence 模式** ,设置```silence=true```可以在权限检测失败时依然执行控制器. 此时需要手动进行权限判定
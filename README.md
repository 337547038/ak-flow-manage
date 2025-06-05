# AK-FLOW 流程管理端

## 开发环境
基于IntellijIDEA 2025.1.x编辑器开发

主要技术：
java、Springboot、Maven、Mybatis、mysql、Fastjson2

| 名称           | 版本    |
|--------------|-------|
| jdk          | 21    |
| Maven        | 3.9.x |
| Spring Boot  | 3.1.x |


## 前端项目

https://github.com/337547038/ak-flow


## 演示地址
https://337547038.github.io/ak-flow/#/

## 调试

1.安装javaSDK，运行demo下的`ak-flow.jar`

2.导入db目录下数据库，连接路径为`jdbc:mysql://localhost:3306/akflow`，用户名:root，密码为空

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/akflow
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

```bash
java -jar ak-flow.jar
```

即可正常在本地调试，源码部分目前有偿提供。友情价：48元，如有需要可加微信`337547038`

**注意：此仓库代码缺少service层，无法正常运行**

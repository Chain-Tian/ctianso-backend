[toc]

## 使用功能

### 主流框架 & 特性

- Spring Boot 2.7.x（贼新）✔
- Spring MVC ✔
- MyBatis + MyBatis Plus 数据访问（开启分页） ✔
- Spring Boot 调试工具和项目处理器 ✔
- Spring AOP 切面编程 ✔
- Spring Scheduler 定时任务 ✔
- Spring 事务注解 ✔

### 数据存储

- MySQL 数据库 ✔
- Redis 内存数据库 ✔
- Elasticsearch 搜索引擎 ✔
- LogStash 进行数据同步
    - 如果存在大量删除业务, 建议Canal

### 工具类

- Hutool 工具库 ✔
- Gson 解析库 ✔
- Apache Commons Lang3 工具类 ✔
- Lombok 注解 ✔

### 业务特性

- Spring Session Redis 分布式登录 ✔
- 全局请求响应拦截器（记录日志） ✔
- 全局异常处理器 ✔
- 自定义错误码 ✔
- 封装通用响应类 ✔
- Swagger + Knife4j 接口文档 ✔
- 自定义权限注解 + 全局校验 ✔
- 全局跨域处理 ✔
- 长整数丢失精度解决 ✔
- 多环境配置 ✔

## 业务功能

- ES灵活检索帖子
- Hutool请求阿B视频接口
- Jsoup+CSS抓取必应图片
- 查询个人Api开放接口平台存在的Api们
- 常用后台增删改查( 接口暴露但是未开放 )

### 设计模式

- 门面模式
    - manager包中的SearchFacade进行搜索的同一处理
    - dataSourceRegistry作为一个存储数据源搜索工具的容器

- 适配器模式

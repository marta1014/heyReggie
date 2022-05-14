# 总结

## 暴露问题

- 更新员工状态时因为js数据处理精度问题导致long类型的id值错误

解决思路

服务端对long类型统一转换为string,拓展spring mvc消息转换器、用JacksonObjectMapper.java对java进行对象到json数据的转换

- 处理公共字段自动填充 获取当前用户

解决思路

基于ThreadLocal封装工具类 在 LoginCheckFilter 的doFilter中 获取当前登陆用户id 调用set方法 设置局部变量的值，然后在
MyMetaObjectHandler 中调用 ThreadLocal 中的get方法获取，以实现用户字段的填充
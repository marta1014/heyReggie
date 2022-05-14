# 总结

## 暴露问题

- 更新员工状态时因为js数据处理精度问题导致long类型的id值错误

解决思路

服务端对long类型统一转换为string,拓展spring mvc消息转换器、用JacksonObjectMapper.java对java进行对象到json数据的转换
package com.simple.reggie.config;

import com.simple.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/FrontSource/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/FrontSource/front/");
    }

    /**
     * 拓展mvc消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器对象
        // 最终把controller方法返回对象(比如R对象)转成相应的json对象然后通过输出流的方式响应给前台
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        // 设置对象转换器 底层是 使用jackson将java转换成json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // 将该消息转换器追加到mvc框架的转换器集合容器中 指定index为0使其优先使用
        converters.add(0,messageConverter);
    }
}

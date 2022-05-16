package com.simple.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//  Slf4j 此注解可直接使用log
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement // 开启事务注解的支持
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("successful ~ my first project is running~");
    }
}

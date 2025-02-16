package com.yyq;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@EnableCaching //开启缓存功能
@Slf4j
public class GraduationDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignApplication.class, args);
    }

}

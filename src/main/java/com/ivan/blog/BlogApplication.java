package com.ivan.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Ivan
 * @Description: 启动类
 * @Date: 2019/10/31 09:38
 */
@SpringBootApplication
@MapperScan("com.ivan.blog.mapper")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}

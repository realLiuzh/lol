package com.lzh.lol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lzh.lol.mapper")
@EnableScheduling   //开启定时任务
public class LolApplication extends SpringBootServletInitializer {
//public class LolApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        //Application的类名
//        return application.sources(LolApplication.class);
//    }
    public static void main(String[] args) {
        SpringApplication.run(LolApplication.class, args);
    }
}

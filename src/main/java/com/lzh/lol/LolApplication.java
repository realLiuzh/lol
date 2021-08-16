package com.lzh.lol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.lzh.lol.mapper")
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

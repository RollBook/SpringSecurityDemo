package com.fall.securitydemo02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fall.securitydemo02.mapper")
public class SecurityDemo02Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo02Application.class, args);
    }

}

package com.fall.securitydemo02.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FAll
 * @date 2023/4/4 16:59
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {return "hello";}
}

package com.fall.securitydemo02.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FAll
 * @date 2023/4/4 16:59
 */

@RestController
public class HelloController {

    @PreAuthorize("hasAuthority('test333')")
    @RequestMapping("/hello")
    public String hello() { return "hello"; }
}

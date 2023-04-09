package com.fall.securitydemo02.controller;

import com.fall.securitydemo02.domain.ResponseResult;
import com.fall.securitydemo02.domain.User;
import com.fall.securitydemo02.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class LoginController {

    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/user/login")
    public ResponseResult<HashMap<String,String>> login(@RequestBody User user) {
        // 登录
        String jwt = loginService.login(user);
        if(Objects.isNull(jwt)) {
            return new ResponseResult<>(401,"Unauthorized");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult<>(200,"登录成功",map);
    }

}

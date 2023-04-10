package com.fall.securitydemo02.service.serviceImpl;

import com.fall.securitydemo02.domain.LoginUser;
import com.fall.securitydemo02.domain.User;
import com.fall.securitydemo02.service.LoginService;
import com.fall.securitydemo02.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager,
                            RedisTemplate<Object,Object> redisTemplate) {

        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;

    }

    @Override
    public String login(User user) {
        // 使用AuthenticationManager的authenticate方法进行认证
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        // 如果认证没通过，则给出提示
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过，则使用userid生成一个jwt
        Object object = authenticate.getPrincipal();
        if(object instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) object;
            String userid = loginUser.getUser().getId().toString();

            String jwt = JWTUtil.createJWT(userid);

            // 把用户信息存入redis, userid作为key
            redisTemplate.opsForValue().set("login:"+userid,loginUser);

            return jwt;

        }



        return null;
    }

    @Override
    public void logout() {
        // 获取SecurityContext中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            Long id = loginUser.getUser().getId();

            // 删除redis中的值
            redisTemplate.opsForValue().getAndDelete("login:"+id);

        }

    }
}

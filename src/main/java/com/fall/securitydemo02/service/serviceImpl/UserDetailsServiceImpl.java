package com.fall.securitydemo02.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fall.securitydemo02.domain.LoginUser;
import com.fall.securitydemo02.domain.User;
import com.fall.securitydemo02.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/4 19:37
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        // 如果没有查询到用户，则抛出异常
        if(Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误la");
        }

        // 2. 查询对应的权限信息
        HashSet<String> list = new HashSet<>(Arrays.asList("test", "admin"));

        // 把数据封装成UserDetails返回


        return new LoginUser(user,list);
    }
}

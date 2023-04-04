package com.fall.securitydemo02;

import com.fall.securitydemo02.domain.User;
import com.fall.securitydemo02.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SecurityDemo02ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

}

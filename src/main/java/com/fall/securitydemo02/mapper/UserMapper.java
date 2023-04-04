package com.fall.securitydemo02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fall.securitydemo02.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author FAll
 * @date 2023/4/4 19:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {



}

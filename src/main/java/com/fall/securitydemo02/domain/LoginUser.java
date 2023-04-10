package com.fall.securitydemo02.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author FAll
 * @date 2023/4/4 19:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private Set<String> permissions;

    @JSONField(serialize = false)   // 不序列化此属性
    private HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();

    public LoginUser(User user, HashSet<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * @author FAll
     * @description 获取所有的权限种类
     * @return: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     * @date 2023/4/10 下午7:03
     */
    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(!grantedAuthorities.isEmpty()) {
            return grantedAuthorities;
        }

        for (String permission : permissions) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission));
        }
        return grantedAuthorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO: 判断登录是否过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

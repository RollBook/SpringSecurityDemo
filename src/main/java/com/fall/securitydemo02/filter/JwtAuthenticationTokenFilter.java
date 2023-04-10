package com.fall.securitydemo02.filter;

import com.fall.securitydemo02.domain.LoginUser;
import com.fall.securitydemo02.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RedisTemplate<Object,Object> redisTemplate;

    public JwtAuthenticationTokenFilter(RedisTemplate<Object,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取并解析token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            // 没有token头，放行但不设置为校验成功
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = JWTUtil.parseJWT(token);
        String userid = claims.getSubject();

        // 从redis中获取用户信息
        Object o = redisTemplate.opsForValue().get("login:" + userid);
        if(o instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) o;

            // 存入SecurityContextHolder (利用三个参数的UsernamePasswordAuthenticationToken构造方法，让认证状态设置为已认证)
            // loginUser.getAuthorities()获取权限信息，并封装到Authentication中
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities()));

            // 完成本过滤器校验，放行
            filterChain.doFilter(request,response);
        } else {
            throw new RuntimeException("用户未登录");
        }


    }
}

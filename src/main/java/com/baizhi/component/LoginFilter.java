package com.baizhi.component;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class LoginFilter extends OncePerRequestFilter {//继承每一次都要经过的过滤器
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String roleId = request.getHeader("roleId");
        if (roleId!=null&&roleId.trim().length()>0){
            filterChain.doFilter(request,response);//有则放行
        }else {
            Response errorResponse = new Response(400, "你的权限不足", null);
            response.setCharacterEncoding("UTF-8");//解决返回值中文乱码问题
            response.setContentType("application/json");//解决返回值中文乱码问题
            response.getWriter().write(JSON.toJSONString(errorResponse));//通过响应把这个写出去,这样写是因为我们的返回值类型是void

        }
    }


}

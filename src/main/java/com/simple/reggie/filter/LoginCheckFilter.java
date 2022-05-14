package com.simple.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.simple.reggie.common.BaseContext;
import com.simple.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器
    final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        String uri = request.getRequestURI();

        Boolean checkRes = check(urls, uri);
        if(checkRes) {
            filterChain.doFilter(request,response);//放行
            return;
        }

        if(request.getSession().getAttribute("employee") != null) {
            log.info("已经登陆路径url： {}",request.getRequestURI());
            log.info("已经登陆路径url： {}",request.getSession().getAttribute("employee"));
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("未登陆路径url： {}",request.getRequestURI());
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    private Boolean check(String[] urls,String reqUri) { // 检查路径匹配
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url,reqUri);

            if (match) {
                return true;
            }
        }
        return false;
    }
}

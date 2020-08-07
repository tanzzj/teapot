package com.teamer.teapot.rbac;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.common.model.PortalUser;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.teamer.teapot.rbac.ContextUtil.USER_PREFIX;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Component
@WebFilter(urlPatterns = "/login")
public class RBACLoginFilter implements Filter {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    RBACConfig rbacConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if (httpServletRequest.getSession().getAttribute(USER_PREFIX) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            BufferedReader streamReader = null;
            streamReader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                stringBuilder.append(inputStr);
            }
            PortalUser user = JSON.toJavaObject(JSON.parseObject(stringBuilder.toString()), PortalUser.class);
            String username = Optional.ofNullable(user.getUsername()).orElseThrow(ContextUserNotFoundException::new);
            String password = MD5Util.encode(
                    Optional.ofNullable(user.getPassword()).orElseThrow(ContextUserNotFoundException::new), rbacConfig.getPasswordSalt()
            );
            PortalUser portalUser = userDAO.queryPortalUser(new PortalUser().setUsername(username));
            if (portalUser != null && portalUser.getPassword().equals(password)) {
                httpServletRequest.getSession().setAttribute(USER_PREFIX, portalUser);
                servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
                ((HttpServletResponse) servletResponse).setStatus(HttpStatus.OK.value());
                try (PrintWriter out = servletResponse.getWriter()) {
                    out.write(JSON.toJSONString(Result.successWithoutData("登录成功")));
                    out.flush();
                }
            } else {
                servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
                ((HttpServletResponse) servletResponse).setStatus(HttpStatus.OK.value());
                try (PrintWriter out = servletResponse.getWriter()) {
                    out.write(JSON.toJSONString(Result.fail("账号名或密码错误", "401")));
                    out.flush();
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}

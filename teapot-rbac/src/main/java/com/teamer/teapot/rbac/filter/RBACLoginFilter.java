package com.teamer.teapot.rbac.filter;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.MD5Util;
import com.teamer.teapot.rbac.RBACConfig;
import com.teamer.teapot.rbac.dao.UserDAO;
import com.teamer.teapot.rbac.exception.ContextUserNotFoundException;
import com.teamer.teapot.rbac.model.TeapotUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.teamer.teapot.rbac.util.ContextUtil.USER_PREFIX;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Component
@WebFilter(urlPatterns = "/api/login")
public class RBACLoginFilter implements Filter {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    RBACConfig rbacConfig;
    private static final String LOGIN_PATH = "/api/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (LOGIN_PATH.equals(httpServletRequest.getRequestURI())) {
            if (httpServletRequest.getSession().getAttribute(USER_PREFIX) != null && !LOGIN_PATH.equals(httpServletRequest.getRequestURI())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String requestData = this.readInputStream(httpServletRequest.getInputStream());
                TeapotUser user = JSON.toJavaObject(JSON.parseObject(requestData), TeapotUser.class);
                String username = Optional.ofNullable(user.getUsername()).orElseThrow(ContextUserNotFoundException::new);
                String password = MD5Util.encode(
                        Optional.ofNullable(user.getPassword()).orElseThrow(ContextUserNotFoundException::new), rbacConfig.getPasswordSalt()
                );
                TeapotUser teapotUser = userDAO.queryPortalUser(new TeapotUser().setUsername(username));
                if (teapotUser != null && teapotUser.getPassword().equals(password)) {
                    httpServletRequest.getSession().setAttribute(USER_PREFIX, teapotUser);
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
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }

    /**
     * 读取inputStream
     *
     * @param inputStream 输入流
     * @return String
     * @throws IOException io异常
     */
    private String readInputStream(InputStream inputStream) throws IOException {
        BufferedReader streamReader = null;
        streamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            stringBuilder.append(inputStr);
        }
        return stringBuilder.toString();
    }
}

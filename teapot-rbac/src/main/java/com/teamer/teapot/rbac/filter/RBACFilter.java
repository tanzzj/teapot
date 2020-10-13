package com.teamer.teapot.rbac.filter;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.rbac.RBACConfig;
import com.teamer.teapot.rbac.model.Role;
import com.teamer.teapot.rbac.model.TeapotUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author : tanzj
 * @date : 2020/7/3.
 */
@Slf4j
//@WebFilter
//@Component
public class RBACFilter implements Filter, InitializingBean {


    @Autowired
    private RBACConfig rbacConfig;

    /**
     * key; resource
     * value: roleList
     */
    private Map<String, List<Role>> resourceMap;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //请求uri
        String requestUri = httpServletRequest.getRequestURI();
        //请求用户
        TeapotUser teapotUser = ((TeapotUser) httpServletRequest.getSession().getAttribute("user"));

        //不需要登录鉴权的情况
        for (String ignoreUri : rbacConfig.getPermitList()) {
            if (ValidationUtil.stringMatcher(ignoreUri, requestUri)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        //判定用户是否已经登录
        this.assertUserLogined(teapotUser, servletResponse);

        //根据实际请求情况判定打回还是通过
        if (isAuthenticated(teapotUser, requestUri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.OK.value());
            try (PrintWriter out = servletResponse.getWriter()) {
                out.write(JSON.toJSONString(Result.fail("无此权限", "403")));
                out.flush();
            }
        }
    }

    /**
     * 判断用户的请求是否被授权
     *
     * @param teapotUser - 当前session用户
     * @param requestUri 目标请求资源
     * @return boolean 有权限返回true/否则返回false
     */
    private boolean isAuthenticated(TeapotUser teapotUser, String requestUri) {
        return teapotUser != null &&
                teapotUser.getRoleList().stream()
                        .anyMatch(requestRole ->
                                resourceMap.keySet().stream()
                                        //过滤出符合条件的资源keySet
                                        .filter(uriPattern -> ValidationUtil.stringMatcher(uriPattern, requestUri))
                                        //取得符合过滤条件的key对应的角色list
                                        .map(resourceMap::get)
                                        //平面化list
                                        .flatMap(Collection::stream)
                                        //判断请求资源是否匹配所需角色
                                        .anyMatch(neededRole -> requestRole.hashCode() == neededRole.hashCode()));
    }

    /**
     * 判定用户是否已经登录
     *
     * @param teapotUser      - 上下文用户
     * @param servletResponse 返回值
     */
    private void assertUserLogined(TeapotUser teapotUser, ServletResponse servletResponse) {
        if (teapotUser == null) {
            servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.OK.value());
            try (PrintWriter out = servletResponse.getWriter()) {
                out.write(JSON.toJSONString(Result.fail("用户未登录", "401")));
                out.flush();
            } catch (IOException e) {
                log.error("返回异常:{}", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.resourceMap = rbacConfig.getResourceMap();
    }
}

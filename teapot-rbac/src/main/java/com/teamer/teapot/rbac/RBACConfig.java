package com.teamer.teapot.rbac;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.rbac.core.RBACRole;
import com.teamer.teapot.rbac.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : tanzj
 * @date : 2020/7/3.
 */
@Slf4j
@Getter
@Setter
@Component("RBACConfig")
@ConfigurationProperties(prefix = "rbac")
public class RBACConfig implements InitializingBean {

    /**
     * 权限资源列表
     */
    private List<Role> resourceList = new ArrayList<>();

    /**
     * 无需进入鉴权的资源uri
     */
    private List<String> permitList = new ArrayList<>();

    /**
     * 密码盐
     */
    private String passwordSalt;

    /**
     * 以permission为key,roleNameList为value的资源列表
     */
    private ConcurrentMap<String, List<RBACRole>> resourceMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        this.resourceMap.clear();
        this.getResourceList().forEach(role ->
                Arrays.stream(role.getResource()).forEach(resource -> {
                    List<RBACRole> roles = Optional.ofNullable(resourceMap.get(resource))
                            .orElseGet(() -> {
                                List<RBACRole> roleList = new ArrayList<>();
                                resourceMap.put(resource, roleList);
                                return roleList;
                            });
                    roles.add(new Role().setRoleId(role.getRoleId()));
                })
        );
        log.info("init:" + JSON.toJSONString(this.resourceMap));
    }
}

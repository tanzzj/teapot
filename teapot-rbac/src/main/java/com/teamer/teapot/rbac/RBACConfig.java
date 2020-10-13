package com.teamer.teapot.rbac;

import com.teamer.teapot.rbac.model.Role;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@Component("RBACConfig")
@ConfigurationProperties(prefix = "rbac")
public class RBACConfig implements InitializingBean {

    /**
     * 权限资源列表
     * 默认为4个角色:portalUser,employee,visitor和counselor
     */
    private List<Role> resourceList = new ArrayList<>(4);

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
    private ConcurrentMap<String, List<Role>> resourceMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        this.resourceMap.clear();
        this.getResourceList().forEach(role ->
                Arrays.stream(role.getResource()).forEach(resource -> {
                    List<Role> roles = Optional.ofNullable(resourceMap.get(resource))
                            .orElseGet(() -> {
                                List<Role> roleList = new ArrayList<>();
                                resourceMap.put(resource, roleList);
                                return roleList;
                            });
                    roles.add(Role.getRole(role.getRoleId()));
                })
        );
    }
}

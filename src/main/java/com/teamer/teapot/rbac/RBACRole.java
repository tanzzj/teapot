package com.teamer.teapot.rbac;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author : tanzj
 * @date : 2020/7/3.
 */
@Slf4j
@Data
@Accessors(chain = true)
public class RBACRole implements Serializable {

    public static final long serialVersionUID = 2653199762765194604L;

    private static final String EMPLOYEE = "employee";
    private static final String PORTAL_USER = "portalUser";
    private static final String VISITOR = "visitor";
    private static final String COUNSELOR = "counselor";

    private static final HashMap<String, RBACRole> ROLE_MAP;

    static {
        ROLE_MAP = new HashMap<String, RBACRole>() {

            {
                put(EMPLOYEE, new RBACRole().setRoleId(EMPLOYEE));
                put(PORTAL_USER, new RBACRole().setRoleId(PORTAL_USER));
                put(VISITOR, new RBACRole().setRoleId(VISITOR));
                put(COUNSELOR, new RBACRole().setRoleId(COUNSELOR));
            }

        };
    }

    private String roleId;
    private String[] resource;

    public static RBACRole employeeRole() {
        return ROLE_MAP.get(EMPLOYEE);
    }

    public static RBACRole portalUserRole() {
        return ROLE_MAP.get(PORTAL_USER);
    }

    public static RBACRole visitorRole() {
        return ROLE_MAP.get(VISITOR);
    }

    public static RBACRole counselorRole() {
        return ROLE_MAP.get(COUNSELOR);
    }

    public static RBACRole getRole(String roleId) {
        return Optional.ofNullable(ROLE_MAP.get(roleId)).orElseThrow(() -> {
            log.error("未找到角色：{}" + roleId);
            return new RuntimeException();
        });
    }
}

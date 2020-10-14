package com.teamer.teapot.rbac.model;

import com.teamer.teapot.rbac.core.RBACRole;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : tanzj
 * @date : 2020/7/3.
 */
@Slf4j
@Data
@Accessors(chain = true)
public class Role implements RBACRole {

    public static final long serialVersionUID = 2653199762765194604L;

    private String roleId;
    private String[] resource;

    @Override
    public String[] getResource() {
        return this.resource;
    }

    @Override
    public String getRoleId() {
        return this.roleId;
    }

    public static Role getRole(String roleId) {
        return null;
    }
}

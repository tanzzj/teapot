package com.teamer.teapot.rbac;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


/**
 * @author : tanzj
 * @date : 2020/5/20.
 */
@Data
@Accessors(chain = true)
public class PortalUser implements Serializable {

    public static final long serialVersionUID = 1L;

    private Integer id;

    private String userId;

    private String username;

    private Integer userType;

    private String realName;

    private String mobile;

    private String password;

    private String address;

    private Integer sex;

    private List<RBACRole> rbacRoleList;


    public PortalUser(String userId) {
        this.userId = userId;
    }

    public PortalUser() {
    }
}

package com.teamer.teapot.rbac.model;


import com.alibaba.fastjson.JSON;
import com.teamer.teapot.rbac.core.RBACUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author : tanzj
 * @date : 2020/5/20.
 */
@Data
@Accessors(chain = true)
public class TeapotUser implements RBACUser, Serializable {

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

    private List<Role> roleList;

    public TeapotUser(String userId) {
        this.userId = userId;
    }

    public TeapotUser() {
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<Role> getRoleList() {
        return this.roleList == null ? new ArrayList<>(0) : this.roleList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

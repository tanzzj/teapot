package com.teamer.teapot.rbac.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * 权限角色接口
 *
 * @author tanzj
 */
public interface RBACUser extends Serializable {

    /**
     * 设置上下文用户名
     *
     * @return UserDetail
     */
    String getUsername();

    /**
     * 设置上下文密码
     *
     * @return UserDetail
     */
    String getPassword();

    /**
     * 取得角色列表
     *
     * @return Collection
     */
    Collection<? extends RBACRole> getRoleList();

}

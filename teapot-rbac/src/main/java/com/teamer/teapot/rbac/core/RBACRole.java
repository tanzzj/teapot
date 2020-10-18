package com.teamer.teapot.rbac.core;

import java.io.Serializable;

public interface RBACRole extends Serializable {

    /**
     * 取得resource列表
     *
     * @return String[]
     */
    String[] getResource();

    /**
     * 取得角色Id
     *
     * @return String
     */
    String getRoleId();

}

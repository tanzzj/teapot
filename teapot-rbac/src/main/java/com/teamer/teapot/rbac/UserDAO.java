package com.teamer.teapot.rbac;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Mapper
public interface UserDAO {

    /**
     * 查询用户
     *
     * @param portalUserParams (username)
     * @return PortalUser
     */
    PortalUser queryPortalUser(PortalUser portalUserParams);

}

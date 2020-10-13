package com.teamer.teapot.rbac.dao;

import com.teamer.teapot.rbac.model.TeapotUser;
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
     * @param teapotUserParams (username)
     * @return PortalUser
     */
    TeapotUser queryPortalUser(TeapotUser teapotUserParams);

}

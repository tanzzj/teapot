package com.teamer.teapot.project.environment.dao;

import com.teamer.teapot.common.model.ProjectEnvironment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tanzj
 * @date 2020/11/22
 */
@Mapper
@Component
public interface ProjectEnvironmentDAO {

    /**
     * 增加项目环境
     *
     * @param projectEnvironment (
     *                           projectId,
     *                           environmentName;
     *                           environmentId;
     *                           environmentHost
     *                           )
     * @return int
     */
    int addProjectEnvironment(ProjectEnvironment projectEnvironment);

    /**
     * 分页模糊查询项目环境列表
     *
     * @param projectEnvironment (
     *                           projectId,
     *                           environmentName;
     *                           environmentId;
     *                           )
     * @return List<ProjectEnvironment>
     */
    List<ProjectEnvironment> queryProjectEnvironmentList(ProjectEnvironment projectEnvironment);
}

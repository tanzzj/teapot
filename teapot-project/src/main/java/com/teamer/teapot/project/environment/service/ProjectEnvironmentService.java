package com.teamer.teapot.project.environment.service;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.ProjectEnvironment;
import com.teamer.teapot.common.model.Result;

/**
 * @author tanzj
 * @date 2020/11/22
 */
public interface ProjectEnvironmentService {


    /**
     * 增加项目环境
     *
     * @param projectEnvironment (
     *                           projectId,
     *                           environmentName;
     *                           environmentId;
     *                           environmentHost
     *                           )
     * @return Result
     */
    Result addProjectEnvironment(ProjectEnvironment projectEnvironment);

    /**
     * 分页模糊查询项目环境列表
     *
     * @param pageParam (
     *                  *projectId,
     *                  environmentName;
     *                  environmentId;
     *                  )
     * @return Result
     */
    Result queryProjectEnvironmentList(PageParam<ProjectEnvironment> pageParam);
}

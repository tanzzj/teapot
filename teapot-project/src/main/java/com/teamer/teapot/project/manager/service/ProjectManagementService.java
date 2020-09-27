package com.teamer.teapot.project.manager.service;

import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Project;

/**
 * @author : tanzj
 * @date : 2020/7/30.
 */
public interface ProjectManagementService {


    /**
     * 分页模糊查询
     *
     * @param projectPageParam params-(
     *                         projectName-项目名
     *                         )
     * @return Result
     */
    Result queryProjectList(PageParam<Project> projectPageParam);

    /**
     * 新建项目
     *
     * @param projectParams (
     *                projectName-项目名
     *                createUser-创建人
     *                )
     * @return int
     */
    Result createProject(Project projectParams);
}

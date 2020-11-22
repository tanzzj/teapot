package com.teamer.teapot.project.environment.service.impl;

import com.github.pagehelper.PageInfo;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectEnvironment;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.project.environment.dao.ProjectEnvironmentDAO;
import com.teamer.teapot.project.environment.service.ProjectEnvironmentService;
import com.teamer.teapot.project.manager.dao.ProjectManagementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author tanzj
 * @date 2020/11/22
 */
@Component
public class ProjectEnvironmentServiceImpl implements ProjectEnvironmentService {

    @Autowired
    ProjectEnvironmentDAO projectEnvironmentDAO;
    @Autowired
    ProjectManagementDAO projectManagementDAO;


    /**
     * 增加项目环境
     * 目前仅支持单环境单实例，需要手工配置
     *
     * @param projectEnvironment (
     *                           projectId,
     *                           environmentName;
     *                           environmentHost;
     *                           )
     * @return Result
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result addProjectEnvironment(ProjectEnvironment projectEnvironment) {
        if (!CollectionUtils.isEmpty(projectManagementDAO.queryProjectList(
                new Project().setProjectId(projectEnvironment.getProjectId())
        ))) {
            String environmentId = UUIDFactory.getShortUUID();
            projectEnvironmentDAO.addProjectEnvironment(
                    new ProjectEnvironment()
                            .setEnvironmentHost(projectEnvironment.getEnvironmentHost())
                            .setEnvironmentId(environmentId)
                            .setEnvironmentName(projectEnvironment.getEnvironmentName())
                            .setProjectId(projectEnvironment.getProjectId())
            );
            return Result.success(new ProjectEnvironment().setEnvironmentId(environmentId));
        } else {
            return Result.fail("project不存在");
        }

    }

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
    @Override
    public Result queryProjectEnvironmentList(PageParam<ProjectEnvironment> pageParam) {
        PageHelperUtil.startPage(pageParam, true);
        return Result.success(new PageInfo<>(projectEnvironmentDAO.queryProjectEnvironmentList(pageParam.getParams())));
    }
}

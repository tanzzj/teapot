package com.teamer.teapot.project.manager.service.impl;

import com.github.pagehelper.PageInfo;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.project.manager.dao.ProjectManagementDAO;
import com.teamer.teapot.project.manager.service.ProjectManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/30.
 */
@Component
public class ProjectManagementServiceImpl implements ProjectManagementService {

    @Autowired
    ProjectManagementDAO projectManagementDAO;

    /**
     * 分页模糊查询
     *
     * @param projectPageParam params-(
     *                         projectName-项目名
     *                         )
     * @return Result
     */
    @Override
    public Result queryProjectList(PageParam<Project> projectPageParam) {
        PageHelperUtil.startPage(projectPageParam, true, "createTime desc");
        List<Project> projectList = projectManagementDAO.queryProjectList(projectPageParam.getParams());
        return Result.success(new PageInfo<>(projectList));
    }

    /**
     * 新建项目
     *
     * @param projectParams (
     *                      projectName-项目名
     *                      createUser-创建人
     *                      )
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result createProject(Project projectParams) {
        projectParams.setProjectId(UUIDFactory.getShortUUID());
        projectManagementDAO.createProject(projectParams);
        return Result.success("create success", new Project().setProjectId(projectParams.getProjectId()));
    }
}

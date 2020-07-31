package com.teamer.teapot.project.manager.controller;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.project.manager.service.ProjectManagementService;
import com.teamer.teapot.rbac.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : tanzj
 * @date : 2020/7/30.
 */
@RestController
@RequestMapping("/api/project/manager")
public class ProjectManagementController {

    @Autowired
    ProjectManagementService projectManagementService;

    /**
     * 分页模糊查询
     *
     * @param pageParam params-(
     *                  projectName-项目名 (optional)
     *                  )
     * @return Result
     */
    @PostMapping("/queryProjectList")
    public Result queryProjectList(@RequestBody PageParam<Project> pageParam) {
        return projectManagementService.queryProjectList(pageParam);
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
    @PostMapping("/createProject")
    public Result createProject(@RequestBody Project projectParams, HttpServletRequest request) {
        ValidationUtil.validateParamsBlankAndNull(projectParams::getProjectName);
//        projectParams.setCreateUser(ContextUtil.getUserFromContext(request).getAccountName());
        projectParams.setCreateUser("tanzj");
        return projectManagementService.createProject(projectParams);
    }

    ;
}

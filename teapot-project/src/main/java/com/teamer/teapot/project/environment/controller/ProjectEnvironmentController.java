package com.teamer.teapot.project.environment.controller;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.ProjectEnvironment;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.project.environment.service.ProjectEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanzj
 * @date 2020/11/22
 */
@RestController
@RequestMapping("/api/project/environment")
public class ProjectEnvironmentController {

    @Autowired
    private ProjectEnvironmentService projectEnvironmentService;

    @PostMapping("/addProjectEnvironment")
    public Result addProjectEnvironment(@RequestBody ProjectEnvironment projectEnvironment) {
        ValidationUtil.validateParamsBlankAndNull(
                projectEnvironment::getEnvironmentHost,
                projectEnvironment::getEnvironmentName,
                projectEnvironment::getProjectId
        );
        return projectEnvironmentService.addProjectEnvironment(projectEnvironment);
    }

    @PostMapping("/queryProjectEnvironmentList")
    public Result queryProjectEnvironmentList(@RequestBody PageParam<ProjectEnvironment> pageParam) {
        ValidationUtil.validateParamsBlankAndNull(pageParam::getParams);
        ValidationUtil.validateParamsBlankAndNull(pageParam.getParams()::getProjectId);
        return projectEnvironmentService.queryProjectEnvironmentList(pageParam);
    }
}

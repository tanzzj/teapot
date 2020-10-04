package com.teamer.teapot.project.manager.service.impl;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.util.TestUtil;
import com.teamer.teapot.project.manager.service.ProjectManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectManagementServiceImplTest {

    @Autowired
    ProjectManagementService projectManagementService;

    @Test
    public void queryProjectList() {
        PageParam<Project> projectPageParam = new PageParam<>();
        projectPageParam.setParams(new Project().setProjectName("tanzj_Test_Project"));
        TestUtil.assertSuccess(
                projectManagementService.queryProjectList(projectPageParam)
        );
    }

    @Test
    @Transactional
    @Rollback
    public void createProject() {
        TestUtil.assertSuccess(
                projectManagementService.createProject(
                        new Project()
                                .setProjectName("测试项目")
                                .setCreateUser("tanzj")
                )
        );
    }
}
package com.teamer.teapot.project.enviroment.service.impl;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.ProjectEnvironment;
import com.teamer.teapot.common.util.TestUtil;
import com.teamer.teapot.project.environment.service.ProjectEnvironmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tanzj
 * @date 2020/11/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectEnvironmentServiceImplTest {

    @Autowired
    ProjectEnvironmentService projectEnvironmentService;


    @Test
    public void addProjectEnvironmentTest() {
        TestUtil.assertFail(
                projectEnvironmentService.addProjectEnvironment(
                        new ProjectEnvironment()
                                .setProjectId("mock")
                                .setEnvironmentName("dev")
                                .setEnvironmentHost("114.116.14.26:9090")
                )
        );

        TestUtil.assertSuccess(
                projectEnvironmentService.addProjectEnvironment(
                        new ProjectEnvironment()
                                .setProjectId("1")
                                .setEnvironmentName("dev")
                                .setEnvironmentHost("114.116.14.26:9090")
                )
        );
    }

    @Test
    public void queryProjectEnvironmentListTest() {
        PageParam<ProjectEnvironment> projectEnvironmentPageParam = new PageParam<>();
        projectEnvironmentPageParam.setParams(
                new ProjectEnvironment()
                        .setProjectId("mock")
        );
        TestUtil.assertSuccess(
                projectEnvironmentService.queryProjectEnvironmentList(projectEnvironmentPageParam)
        );
    }

}

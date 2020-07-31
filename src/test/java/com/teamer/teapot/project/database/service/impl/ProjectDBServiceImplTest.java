package com.teamer.teapot.project.database.service.impl;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectDatabase;
import com.teamer.teapot.common.util.TestUtil;
import com.teamer.teapot.project.database.service.ProjectDBService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectDBServiceImplTest {

    @Autowired
    ProjectDBService projectDBService;

    @Test
    @Transactional
    @Rollback
    public void addProjectDatabaseInstance() {
        TestUtil.assertSuccess(
                projectDBService.addProjectDatabaseInstance(
                        new ProjectDatabase().setDatabaseId("123")
                                .setProjectId("1")
                )
        );
    }

    @Test
    public void queryProjectDataBaseList() {
        TestUtil.assertSuccess(
                projectDBService.queryProjectDataBaseList(
                        new Project().setProjectId("1")
                )
        );
    }
}
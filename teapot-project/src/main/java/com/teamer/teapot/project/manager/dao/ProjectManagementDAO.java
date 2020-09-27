package com.teamer.teapot.project.manager.dao;

import com.teamer.teapot.common.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/30.
 */
@Mapper
@Component
public interface ProjectManagementDAO {

    /**
     * 分页查询项目列表
     *
     * @param project (projectName-项目名)
     * @return List<Project>
     */
    List<Project> queryProjectList(Project project);

    /**
     * 新建项目
     *
     * @param project (
     *                projectId-项目id
     *                projectName-项目名
     *                createUser-创建人
     *                )
     * @return int
     */
    int createProject(Project project);
}

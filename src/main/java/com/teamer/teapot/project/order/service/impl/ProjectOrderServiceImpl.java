package com.teamer.teapot.project.order.service.impl;

import com.github.pagehelper.PageInfo;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.project.order.dao.ProjectOrderDAO;
import com.teamer.teapot.project.order.service.ProjectOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Slf4j
@Service
public class ProjectOrderServiceImpl implements ProjectOrderService {

    @Autowired
    private ProjectOrderDAO projectOrderDAO;

    private static final Integer PENDING = 0;

    /**
     * 查询项目工单列表
     *
     * @param projectPageParam (
     *                         projectId-项目id
     *                         projectOrderName-工单名称[ 可选 ] *
     *                         )
     * @return Result
     */
    @Override
    public Result queryProjectOrderList(PageParam<ProjectOrder> projectPageParam) {
        PageHelperUtil.startPage(projectPageParam, true, "createTime desc");
        List<ProjectOrder> projectOrderList = projectOrderDAO.queryProjectOrderList(projectPageParam.getParams());
        return Result.success(new PageInfo<>(projectOrderList));
    }

    /**
     * 查询订单详情
     *
     * @param projectOrderParams (projectOrderId-项目工单id)
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result queryProjectOrderDetails(ProjectOrder projectOrderParams) {
        ProjectOrder projectOrder = projectOrderDAO.queryProjectOrderDetails(projectOrderParams);
        return projectOrder != null ?
                Result.success(projectOrder) :
                Result.fail("order not exist");
    }

    /**
     * 创建项目工单
     *
     * @param projectOrder - 项目工单
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result createProjectOrder(ProjectOrder projectOrder) {
        projectOrder
                .setProjectOrderId(UUIDFactory.getShortUUID())
                .setOrderState(PENDING);
        projectOrderDAO.createProjectOrder(projectOrder);
        Optional.ofNullable(projectOrder.getTag()).ifPresent(tagList -> {
            tagList.forEach(eachTag -> {
                eachTag.setTagId(UUIDFactory.getShortUUID());
                eachTag.setProjectId(projectOrder.getProjectId());
            });
            projectOrderDAO.insertProjectTags(projectOrder);
        });
        return Result.success(new ProjectOrder().setProjectOrderId(projectOrder.getProjectOrderId()));
    }
}

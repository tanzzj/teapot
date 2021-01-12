package com.teamer.teapot.project.order.service.impl;

import com.teamer.teapot.common.exception.BusinessException;
import com.teamer.teapot.common.model.*;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.datasource.DatabaseContext;
import com.teamer.teapot.project.order.dao.ProjectOrderLogsDAO;
import com.teamer.teapot.project.order.service.ProjectOrderLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author tanzj
 * @date 2020/12/8
 */
@Slf4j
@Component
public class ProjectOrderLogsServiceImpl implements ProjectOrderLogsService {

    @Autowired
    private ProjectOrderLogsDAO projectOrderLogsDAO;

    /**
     * 查询项目工单记录
     *
     * @param pageParam params-(projectOrderId)
     * @return List < OrderLogs >
     */
    @Override
    public Result queryProjectOrderLogs(PageParam<OrderLogs> pageParam) {
        PageHelperUtil.startPage(pageParam, true, "createTime asc");
        List<OrderLogs> orderLogsList = projectOrderLogsDAO.queryProjectOrderLogs(
                new OrderLogs().setProjectOrderId(pageParam.getParams().getProjectOrderId())
        );
        return Result.success(orderLogsList);
    }

    /**
     * 新增项目工单记录
     *
     * @param orderLogs (logsId, projectOrderId, details, creator, updater)
     * @return Result
     */
    @Override
    public Result insertProjectOrderLogs(OrderLogs orderLogs) {
        projectOrderLogsDAO.insertOrderLogs(orderLogs.setLogsId(UUIDFactory.getShortUUID()));
        return Result.success(new OrderLogs().setLogsId(orderLogs.getLogsId()));
    }

    /**
     * 执行工单
     *
     * @param projectOrder (projectId,databaseId)
     * @return Result
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result executeOrder(ProjectOrder projectOrder) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<OrderLogs> orderLogsList = projectOrderLogsDAO.queryProjectOrderLogs(
                new OrderLogs().setProjectOrderId(projectOrder.getProjectOrderId())
        );

        orderLogsList.forEach(eachOrderLog -> {
            boolean alreadyExecute = newArrayList(Optional.ofNullable(eachOrderLog.getExecuteLog()).orElse("")
                    .split(";"))
                    .stream()
                    .anyMatch(eachDatabaseId -> eachDatabaseId.equals(projectOrder.getDatabaseId()));
            if (alreadyExecute) {
                throw new BusinessException("current order cannot be executed");
            }
            sqlBuilder.append(eachOrderLog.getDetails()).append(";");
        });

        try {
            Result result = DatabaseContext.executeSql(
                    new SQLParams()
                            .setDatabaseId(projectOrder.getDatabaseId())
                            .setSql(sqlBuilder.toString())
            );

            orderLogsList.forEach(orderLogs ->
                    projectOrderLogsDAO.updateOrderLogs(
                            new OrderLogs()
                                    .setLogsId(orderLogs.getLogsId())
                                    .setExecuteLog(
                                            Optional.ofNullable(orderLogs.getExecuteLog()).orElse("") + ";" + projectOrder.getDatabaseId()
                                    )
                    )
            );
            return result;
        } catch (SQLException e) {
            log.error("sql 执行失败", e);
            return Result.fail("sql 执行失败", e);
        }
    }

}

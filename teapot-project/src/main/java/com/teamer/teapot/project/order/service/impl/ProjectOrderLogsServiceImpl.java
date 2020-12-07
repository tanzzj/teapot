package com.teamer.teapot.project.order.service.impl;

import com.teamer.teapot.common.model.OrderLogs;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.project.order.dao.ProjectOrderLogsDAO;
import com.teamer.teapot.project.order.service.ProjectOrderLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tanzj
 * @date 2020/12/8
 */
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
}

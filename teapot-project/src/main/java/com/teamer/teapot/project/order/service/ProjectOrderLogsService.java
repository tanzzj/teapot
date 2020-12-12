package com.teamer.teapot.project.order.service;

import com.teamer.teapot.common.model.OrderLogs;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Result;

/**
 * @author tanzj
 * @date 2020/12/8
 */
public interface ProjectOrderLogsService {


    /**
     * 查询项目工单记录
     *
     * @param pageParam params-(projectOrderId)
     * @return List < OrderLogs >
     */
    Result queryProjectOrderLogs(PageParam<OrderLogs> pageParam);

    /**
     * 新增项目工单记录
     *
     * @param orderLogs (logsId, projectOrderId, details, creator, updater)
     * @return Result
     */
    Result insertProjectOrderLogs(OrderLogs orderLogs);
}

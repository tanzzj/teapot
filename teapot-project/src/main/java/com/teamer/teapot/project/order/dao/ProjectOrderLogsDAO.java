package com.teamer.teapot.project.order.dao;

import com.teamer.teapot.common.model.OrderLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@Mapper
public interface ProjectOrderLogsDAO {

    /**
     * 查询项目工单记录
     *
     * @param orderLogs (projectOrderId)
     * @return List < OrderLogs >
     */
    List<OrderLogs> queryProjectOrderLogs(OrderLogs orderLogs);

    /**
     * 新增工单记录
     *
     * @param orderLogs (logsId, projectOrderId, details, creator, updater)
     * @return int
     */
    int insertOrderLogs(OrderLogs orderLogs);

}

package com.teamer.teapot.common.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author tanzj
 * @date 2020/12/8
 */
@Data
@Accessors(chain = true)
public class OrderLogs {

    private Integer id;
    private String logsId;
    private String projectOrderId;
    private String description;
    private String details;
    private String creator;
    private String updater;
    private Date createTime;
    private Date updateTime;
    private String executeLog;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

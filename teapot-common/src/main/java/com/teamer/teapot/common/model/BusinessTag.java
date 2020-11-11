package com.teamer.teapot.common.model;

import lombok.Data;

import java.util.List;

/**
 * @author tanzj
 * @date 2020/11/10
 */
@Data
public class BusinessTag {

    private String businessId;
    private String channel;
    private List<Tag> tagList;

}

package com.teamer.teapot.core.tag.service;

import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.dto.TagParams;

/**
 * 标签业务逻辑
 *
 * @author tanzj
 */
public interface TagService {

    /**
     * 根据id更新tagValue，
     * 或根据channel-tagKey-tagValue更新tagId与businessId的关联关系
     * 若不存在则会根据channel-tagKey-tagValue去创建一个tag，然后绑定关联关系
     *
     * @param tagParams (
     *                  channel-来源,
     *                  tagKey-标签键,
     *                  tagValue-标签值,
     *                  )
     * @return Result
     */
    Result updateTag(TagParams tagParams);

    /**
     * 根据businessId、channel 查询tag列表
     *
     * @param tagParams (
     *                  * businessId-业务id,
     *                  channel-业务域
     *                  )
     * @return Result
     */
    Result queryBusinessTag(TagParams tagParams);
}

package com.teamer.teapot.core.tag.dao;

import com.teamer.teapot.common.model.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tanzj
 * @date 2020/11/9
 */
@Mapper
public interface TagDAO {

    /**
     * 查询tag
     *
     * @param tag (businessId,tagId)
     * @return Tag
     */
    Tag queryBusinessTagMapping(Tag tag);

    /**
     * 根据channel,tagKey,tagValue查询tag
     *
     * @param tag (
     *            tagKey,
     *            tagValue,
     *            channel
     *            )
     * @return Tag
     */
    Tag queryTag(Tag tag);

    /**
     * 更新tag与业务id关联关系
     *
     * @param tag (tagId,businessId)
     * @return int
     */
    int insertTagMapping(Tag tag);

    /**
     * 插入tag
     *
     * @param tag (tagId,tagKey,tagValue,channel)
     * @return int
     */
    int insertTag(Tag tag);

    /**
     * 根据tagId更新tagValue
     *
     * @param tag (tagId,tagValue)
     * @return int
     */
    int updateTagValue(Tag tag);

}

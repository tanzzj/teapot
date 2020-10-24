package com.teamer.teapot.core.tag.service.impl;

import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.Tag;
import com.teamer.teapot.common.model.dto.TagParams;
import com.teamer.teapot.common.model.enums.TagEnum;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.core.tag.dao.TagDAO;
import com.teamer.teapot.core.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author tanzj
 */
@Component
public class TagServiceImpl implements TagService {

    @Autowired
    TagDAO tagDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateTag(TagParams tagParams) {
        //根据tagId更新tagValue的情况
        if (tagParams.getTagId() != null) {
            tagDAO.updateTagValue(new Tag().setTagId(tagParams.getTagId()).setTagValue(tagParams.getTagValue()));
            return Result.success(new Tag().setTagId(tagParams.getTagId()));
        } else {
            String tagId = null;
            Tag tagDO = tagDAO.queryTag(generateTagQuery(tagParams));
            if (tagDO != null) {
                tagId = tagDO.getTagId();
                tagDAO.insertTagMapping(generateNewTagMapping(tagParams));
            } else {
                Tag newTag = generateNewTag(tagParams);
                tagId = newTag.getTagId();
                tagDAO.insertTag(generateNewTag(tagParams));
                tagDAO.insertTagMapping(generateNewTagMapping(tagParams.setTagId(tagId)));
            }
            return Result.success(new Tag().setTagId(tagId));
        }
    }

    @Override
    public Result queryBusinessTag(TagParams tagParams) {
        return Result.success(
                tagDAO.queryTag(
                        new Tag().setBusinessId(tagParams.getBusinessId())
                                .setChannel(Optional.ofNullable(tagParams.getChannel())
                                        .orElse(TagEnum.TagChannelEnum.OTHER).getName())
                ));
    }

    /**
     * 根据tagChannel-tagKey-tagValue创建查询对象
     *
     * @param tagParams (channel,tagKey,tagValue)
     * @return Tag
     */
    private Tag generateTagQuery(TagParams tagParams) {
        return new Tag()
                .setChannel(tagParams.getChannel().getName())
                .setTagKey(tagParams.getTagKey())
                .setTagValue(tagParams.getTagValue());
    }

    /**
     * 根据tagChannel-tagKey-tagValue创建一个新tag对象
     *
     * @param tagParams (channel,tagKey,tagValue)
     * @return Tag
     */
    private Tag generateNewTag(TagParams tagParams) {
        return new Tag()
                .setTagId(UUIDFactory.getShortUUID())
                .setTagKey(tagParams.getTagKey())
                .setTagValue(tagParams.getTagValue())
                .setChannel(tagParams.getChannel().getName());
    }

    /**
     * 构造tag和businessId的关联关系
     *
     * @param tagParams (
     *                  businessId-业务id,
     *                  tagId-标签id
     *                  )
     * @return Tag
     */
    private Tag generateNewTagMapping(TagParams tagParams) {
        return new Tag()
                .setBusinessId(tagParams.getBusinessId())
                .setTagId(tagParams.getTagId());
    }
}

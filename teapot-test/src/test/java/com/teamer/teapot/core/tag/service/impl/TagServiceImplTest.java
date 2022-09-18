package com.teamer.teapot.core.tag.service.impl;

import com.teamer.teapot.common.model.dto.TagParams;
import com.teamer.teapot.common.model.enums.TagEnum;
import com.teamer.teapot.common.util.TestUtil;
import com.teamer.teapot.core.tag.service.TagService;
import com.teamer.teapot.core.utils.SpringContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tanzj
 * @date 2020/11/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TagServiceImplTest {

    @Autowired
    TagService tagService;
    @Autowired
    SpringContextHolder springContextHolder;

    @Test
    public void updateTagTest() {
        TestUtil.assertSuccess(
            tagService.updateTag(new TagParams()
                .setTagKey("key")
                .setTagValue("value")
                .setChannel(TagEnum.TagChannelEnum.valueOf("ORDER"))
                .setBusinessId("tanzj")
            )
        );
    }

    @Test
    public void queryBusinessTagTest() {
        TestUtil.assertSuccess(
            tagService.queryBusinessTag(new TagParams().setBusinessId("tanzj"))
        );
    }

    @Test
    public void applicationTest() {
        ApplicationContext applicationContext = springContextHolder.getApplicationContext();
        TagService bean = applicationContext.getBean(TagService.class);
        assert bean != null;
        System.out.println();
    }


}

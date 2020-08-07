package com.teamer.teapot.project.order.service.impl;

import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.ProjectOrder;
import com.teamer.teapot.common.model.ProjectOrderTag;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.TestUtil;
import com.teamer.teapot.project.order.service.ProjectOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author : tanzj
 * @date : 2020/8/7.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectOrderServiceImplTest {

    @Autowired
    ProjectOrderService projectOrderService;

    @Test
    public void queryProjectOrderList() {
        PageParam<ProjectOrder> projectOrderPageParam = new PageParam<>();
        projectOrderPageParam.setParams(new ProjectOrder().setProjectId("1"));
        TestUtil.assertSuccess(
                projectOrderService.queryProjectOrderList(projectOrderPageParam)
        );
    }

    @Test
    public void queryProjectOrderDetails() {
        TestUtil.assertSuccess(
                projectOrderService.queryProjectOrderDetails(new ProjectOrder().setProjectOrderId("1"))
        );
        //must fail
        TestUtil.assertFail(
                projectOrderService.queryProjectOrderDetails(new ProjectOrder().setProjectOrderId("2"))
        );
    }

    @Test
    @Transactional
    @Rollback
    public void createProjectOrder() {
        TestUtil.assertSuccess(
                projectOrderService.createProjectOrder(
                        new ProjectOrder()
                                .setProjectId("1")
                                .setProjectOrderName("666")
                                .setProjectOrderDetail("ui")
                                .setContent("select * from t_portal")
                                .setOrderType(1 /*1-db模式*/)
                                .setCreateUser("tanzj")
                                .setCreateUserId("1")
                                .setTag(new ArrayList<ProjectOrderTag>() {{
                                    add(new ProjectOrderTag().setOrderTag("666"));
                                    add(new ProjectOrderTag().setOrderTag("777"));
                                }})
                )
        );
    }

    @Test
    @Transactional
    @Rollback
    public void examineProjectOrder() {
        // step 1 : create a project order
        Result createResult = projectOrderService.createProjectOrder(
                new ProjectOrder()
                        .setProjectId("1")
                        .setProjectOrderName("666")
                        .setProjectOrderDetail("ui")
                        .setContent("select * from t_portal")
                        .setOrderType(1 /*1-db模式*/)
                        .setCreateUser("tanzj")
                        .setCreateUserId("1")
                        .setTag(new ArrayList<ProjectOrderTag>() {{
                            add(new ProjectOrderTag().setOrderTag("666"));
                            add(new ProjectOrderTag().setOrderTag("777"));
                        }})
        );
        String projectOrderId = ((ProjectOrder) createResult.getData()).getProjectOrderId();

        // step 2: examine project order to pass
        TestUtil.assertSuccess(
                projectOrderService.examineProjectOrder(
                        new ProjectOrder()
                                .setProjectOrderId(projectOrderId)
                                .setOrderState(ProjectOrder.OrderStateEnum.PASS.getValue())
                                .setUpdateUser("tanzj")
                )
        );

        // step 3 : assert project order's state is pass
        ((ProjectOrder) projectOrderService.queryProjectOrderDetails(
                new ProjectOrder().setProjectOrderId(projectOrderId)
        ).getData()).getOrderState().equals(ProjectOrder.OrderStateEnum.PASS.getValue());

        // step 4 : assert fail while order update result != 1
        TestUtil.assertFail(
                projectOrderService.examineProjectOrder(new ProjectOrder().setOrderState(ProjectOrder.OrderStateEnum.PASS.getValue()))
        );


    }
}
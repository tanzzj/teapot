package com.teamer.teapot;

import com.teamer.teapot.common.util.MD5Util;
import com.teamer.teapot.rbac.RBACConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeapotApplicationTests {

    @Autowired
    RBACConfig rbacConfig;

    @Test
    public void contextLoads() {
        System.out.println("the password is :" + MD5Util.encode("tanzj", rbacConfig.getPasswordSalt()));
    }

}

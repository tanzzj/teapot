package com.teamer.teapot.core.envs.controller;

import com.teamer.teapot.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.teamer.teapot.core.envs.SystemEnvConstant.ENV_LIST;


/**
 * @author tanzj
 * @date 2021/1/13
 */
@RestController
@RequestMapping("/api/system")
public class SystemEnvController {

    @GetMapping("/querySystemEnv")
    public Result querySystemEnv() {
        return Result.success(ENV_LIST);
    }

}

package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author tanzj
 * @date 2022/8/2
 */
@Data
@Accessors(chain = true)
public class ActionBO {

    private Map<String, Object> actionContext;
    private ActionEnum actionType;


    public enum ActionEnum {
        /**
         * 默认
         */
        DEFAULT,

        /**
         * bean调用
         */
        BEAN_INVOKE,

        /**
         * 方法调用
         */
        METHOD_INVOKE
    }

}

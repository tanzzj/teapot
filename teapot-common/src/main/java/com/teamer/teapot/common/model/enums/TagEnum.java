package com.teamer.teapot.common.model.enums;

/**
 * @author tanzj
 * @date 2020/11/9
 */
public class TagEnum {

    /**
     * 标签来源枚举
     */
    public enum TagChannelEnum {

        /**
         * 订单模块
         */
        ORDER("order"),

        /**
         * 其他
         */
        OTHER("other");
        String name;

        public String getName() {
            return name;
        }

        TagChannelEnum(String name) {
            this.name = name;
        }
    }

}

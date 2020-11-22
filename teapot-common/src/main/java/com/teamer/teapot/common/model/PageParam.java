package com.teamer.teapot.common.model;

import com.teamer.teapot.common.annoation.EnumValidator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页传参实体
 *
 * @author luje
 */
public class PageParam<T> {

    @Min(1)
    private int pageNum = 1;

    @Min(1)
    @Max(100)
    private int pageSize = 10;

    private String orderType;

    @EnumValidator(value = OrderMethodEnum.class)
    private String orderMethod = OrderMethodEnum.asc.toString();

    private T params;

    public int getPageNum() {
        return pageNum;
    }

    public PageParam<T> setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageParam<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public PageParam<T> setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getOrderMethod() {
        return orderMethod;
    }

    public PageParam<T> setOrderMethod(String orderMethod) {
        this.orderMethod = orderMethod;
        return this;
    }

    public T getParams() {
        return params;
    }

    public PageParam<T> setParams(T params) {
        this.params = params;
        return this;
    }

    /**
     * 分页 OrderMethod 枚举
     *
     * @author tanzj
     */
    public enum OrderMethodEnum {

        /**
         * 升序
         */
        asc("asc"),

        /**
         * 降序
         */
        desc("desc");


        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        OrderMethodEnum(String value) {
            this.value = value;
        }

        OrderMethodEnum() {
        }
    }
}

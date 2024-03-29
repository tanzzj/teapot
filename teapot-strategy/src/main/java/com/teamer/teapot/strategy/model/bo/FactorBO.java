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
public class FactorBO {

    private Long id;
    private String factorName;
    private String factorCode;
    private String factorType;
    private Map<String, Object> extend;

}

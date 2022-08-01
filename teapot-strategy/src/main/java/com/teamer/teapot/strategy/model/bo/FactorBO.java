package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

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

}

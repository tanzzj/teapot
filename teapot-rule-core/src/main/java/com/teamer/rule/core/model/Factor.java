package com.teamer.rule.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2023/9/30
 */
@Data
@Accessors(chain = true)
public class Factor {

    private String factorName;
    private String factorDomain;
    private String factorModel;
    private String factorType;

}

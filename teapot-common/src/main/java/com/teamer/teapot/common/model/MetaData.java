package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author : tanzj
 * @date : 2020/8/10.
 */
@Data
@Accessors(chain = true)
public class MetaData {

    private String name;
    private Integer mysqlType;
    private Boolean primaryKey;
    private Boolean blob;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaData metaData = (MetaData) o;
        return Objects.equals(name, metaData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

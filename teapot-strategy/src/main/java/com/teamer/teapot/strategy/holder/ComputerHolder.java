package com.teamer.teapot.strategy.holder;


import com.teamer.teapot.strategy.constant.ComputeType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author tanzj
 * @date 2022/8/6
 */
public class ComputerHolder {

    private static final Map<ComputeType, BiFunction<Object, Object, Boolean>> COMPUTE_MAP = new HashMap<>();

    static {
        COMPUTE_MAP.put(ComputeType.EQUAL, Object::equals);
        COMPUTE_MAP.put(ComputeType.NOT_EQUAL, (params, expect) -> !Objects.equals(params, expect));
        COMPUTE_MAP.put(ComputeType.GREATER_THAN, (params, expect) ->
            new BigDecimal(String.valueOf(params)).compareTo(new BigDecimal(String.valueOf(expect))) > 0
        );
        COMPUTE_MAP.put(ComputeType.GREATER_THAN_OR_EQUAL_TO, (params, expect) ->
            new BigDecimal(String.valueOf(params)).compareTo(new BigDecimal(String.valueOf(expect))) >= 0
        );
        COMPUTE_MAP.put(ComputeType.LESS_THAN, (params, expect) ->
            new BigDecimal(String.valueOf(params)).compareTo(new BigDecimal(String.valueOf(expect))) < 0
        );
        COMPUTE_MAP.put(ComputeType.LESS_THAN_OR_EQUAL_TO, (params, expect) ->
            new BigDecimal(String.valueOf(params)).compareTo(new BigDecimal(String.valueOf(expect))) <= 0
        );

        COMPUTE_MAP.put(ComputeType.IN, (params, expect) -> {
                List<?> expectList = (List<?>) expect;
                return expectList.contains(params);
            }
        );

        COMPUTE_MAP.put(ComputeType.NOT_IN, (params, expect) -> {
                List<?> expectList = (List<?>) expect;
                return !expectList.contains(params);
            }
        );


        COMPUTE_MAP.put(ComputeType.TIME_AFTER, (params, expect) -> {
                Long expectTimeStamp = (long) expect;
                Long paramsTimeStamp = (Long) params;
                return paramsTimeStamp - expectTimeStamp >= 0;
            }
        );

        COMPUTE_MAP.put(ComputeType.TIME_BEFORE, (params, expect) -> {
                Long expectTimeStamp = (Long) expect;
                Long paramsTimeStamp = (Long) params;
                return paramsTimeStamp - expectTimeStamp <= 0;
            }
        );
    }

    private static BiFunction<Object, Object, Boolean> getOperator(ComputeType computeType) {
        return COMPUTE_MAP.get(computeType);
    }
}

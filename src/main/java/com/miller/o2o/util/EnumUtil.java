package com.miller.o2o.util;

import com.miller.o2o.common.BaseState;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
public class EnumUtil {

    private EnumUtil() {

    }

    public <E extends BaseState> E stateOf(Class<E> enumClass, int state){
        for (E e : enumClass.getEnumConstants()) {
            if (e.getState() == state) {
                return e;
            }
        }
        return null;
    }
}

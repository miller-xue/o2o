package com.miller.o2o.util;

/**
 * Created by miller on 2019/3/12
 *
 * @author Miller
 */
public class PageCalculator {

    /**
     * 分页参数转换工具，无用。使用pageHelper
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIdnex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}

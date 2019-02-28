package com.miller.o2o.dao.split;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by miller on 2019/2/28
 * 动态数据源容器
 * @author Miller
 */
@Slf4j
public class DynamicDataSourceHolder {

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static final String DB_MASTER = "master";

    public static final String DB_SLAVE = "slave";

    /**
     * 获取线程的dbType
     * @return
     */
    public static Object getDbType() {
        String db = contextHolder.get();
        if (db == null) {
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * 设置线程的dbType
     * @param string
     */
    public static void setDbType(String string) {
        log.debug("所使用的数据源为：" + string);
        contextHolder.set(string);
    }

    /**
     * 清理连接类型
     */
    public static void clearDbType() {
        contextHolder.remove();
    }
}

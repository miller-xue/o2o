package com.miller.o2o.dao;

import com.miller.o2o.entity.Area;

import java.util.List;

/**
 * Created by miller on 2019/2/11
 *
 * @author Miller
 */
public interface AreaDao {

    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryAll();
}

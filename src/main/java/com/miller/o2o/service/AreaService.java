package com.miller.o2o.service;

import com.miller.o2o.entity.Area;

import java.util.List;

/**
 * Created by miller on 2019/2/11
 *
 * @author Miller
 */
public interface AreaService {

    /**
     * 获取所有区域列表
     * @return
     */
    List<Area> getList();
}

package com.miller.o2o.service.impl;

import com.miller.o2o.dao.AreaDao;
import com.miller.o2o.entity.Area;
import com.miller.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by miller on 2019/2/11
 *
 * @author Miller
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getList() {
        return areaDao.queryAll();
    }
}

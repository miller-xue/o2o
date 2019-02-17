package com.miller.o2o.service.impl;

import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.Area;
import com.miller.o2o.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/2/11
 *
 * @author Miller
 */
public class AreaServiceImplTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void getList() {
        List<Area> list = areaService.getList();
        assertEquals(0, list.size());
    }
}
package com.miller.o2o.dao;

import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/3/25
 *
 * @author Miller
 */
public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void select() {
        List<HeadLine> select = headLineDao.select(new HeadLine());
        assertEquals(0, select.size());
    }

    @Test
    public void selectById() {
        HeadLine headLine = headLineDao.selectById(1);
        assertEquals(null, headLine);
    }
}
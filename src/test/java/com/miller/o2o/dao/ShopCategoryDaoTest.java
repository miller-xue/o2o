package com.miller.o2o.dao;

import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/2/25
 *
 * @author Miller
 */
public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private ShopCategoryDao dao;

    @Test
    public void queryList() {
        List<ShopCategory> shopCategories = dao.queryList(null);

    }
}
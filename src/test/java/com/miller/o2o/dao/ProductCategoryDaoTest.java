package com.miller.o2o.dao;

import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao categoryDao;

    @Test
    public void selectByShopId() {
        List<ProductCategory> productCategories = categoryDao.selectList(4);
        assertEquals(5,productCategories.size());
    }

    @Test
    public void batchInsert() {
        List<ProductCategory> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(ProductCategory.builder().name("分类" + i).priority(i).shopId(4l).build());
        }
        int i = categoryDao.batchInsert(list);
        assertTrue(i > 0);
    }

    @Test
    @Ignore
    public void delete() {
        int delete = categoryDao.delete(4, 4);
        assertEquals(1, delete);
    }
}
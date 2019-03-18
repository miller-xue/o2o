package com.miller.o2o.dao;

import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.Product;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testAInsert() {
        Product product = Product.builder().name("product").desc("desc").imgAddr("addr").normalPrice("111").promotionPrice("222")
                .priority(11).productCategory(ProductCategory.builder().id(3L).build())
                .shop(Shop.builder().id(4L).build()).build();

        int insert = productDao.insert(product);
        assertEquals(1,insert);
    }
}
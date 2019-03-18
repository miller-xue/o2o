package com.miller.o2o.dao;

import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest
{

    @Autowired
    private ProductImgDao imgDao;

    @Test
    public void testABatchInsert() {
        List<ProductImg> imgList = new ArrayList<>();
        imgList.add(ProductImg.builder().addr("addr1").desc("desc1").priority(1).productId(1L).build());
        imgList.add(ProductImg.builder().addr("addr2").desc("desc2").priority(2).productId(1L).build());
        int i = imgDao.batchInsert(imgList);
        assertEquals(2, i);
    }

    @Test
    public void testBSelectList() {
        List<ProductImg> productImgs = imgDao.selectList(1);
        assertEquals(2, productImgs.size());
    }

    @Test
    public void testCDeleteByProductId() {
        int i = imgDao.deleteByProductId(1l);
        assertEquals(2, i);
    }
}
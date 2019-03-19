package com.miller.o2o.service.impl;

import com.miller.o2o.BaseTest;
import com.miller.o2o.dto.ImageHolder;
import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.enums.ProductStateEnum;
import com.miller.o2o.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/3/19
 *
 * @author Miller
 */
public class ProductServiceImplTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void add() throws FileNotFoundException {
        Product product = Product.builder().name("product").desc("desc").imgAddr("addr").normalPrice("111").promotionPrice("222")
                .priority(11).productCategory(ProductCategory.builder().id(3L).build())
                .shop(Shop.builder().id(4L).build()).build();
        ImageHolder imageHolder = new ImageHolder("test.jpg", new FileInputStream(new File("C:\\Users\\Miller\\Desktop\\2k壁纸\\wallhaven-716116.jpg")));
        ImageHolder imageHolder2 = new ImageHolder("test.jpg", new FileInputStream(new File("C:\\Users\\Miller\\Desktop\\2k壁纸\\wallroom-3840x2400-bg-0a2a140.jpg")));
        ProductExecution add = productService.add(product, imageHolder, Arrays.asList(imageHolder2));
        assertEquals(add.getState(), ProductStateEnum.SUCCESS.getState());
    }
}
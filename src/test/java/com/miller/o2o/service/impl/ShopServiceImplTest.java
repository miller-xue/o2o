package com.miller.o2o.service.impl;

import com.miller.o2o.BaseTest;
import com.miller.o2o.dto.ShopExecution;
import com.miller.o2o.entity.Area;
import com.miller.o2o.entity.PersonInfo;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.entity.ShopCategory;
import com.miller.o2o.enums.ShopStateEnum;
import com.miller.o2o.service.ShopService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public class ShopServiceImplTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    @Ignore
    public void add() throws FileNotFoundException {
        Shop build = Shop.builder().name("小吃城").desc("我的小吃城").addr("南沣村").phone("13022996276").img("").
                priority(10).enableStatus(1).advice("无").
                area(Area.builder().id(1).build())
                .owner(PersonInfo.builder().id(1L).build()).
                        shopCategory(ShopCategory.builder().id(1l).build()).build();

        ShopExecution add = shopService.add(build, new FileInputStream(new File("C:\\Users\\Miller\\Desktop\\2k壁纸\\wallhaven-716116.jpg")), "test.jpg");
        assertEquals(ShopStateEnum.CHECK.getState(), add.getState());
    }

    @Test
    @Ignore
    @Transactional
    public void modifyShop() throws FileNotFoundException {
        Shop shop = Shop.builder().name("修改后店铺名字").id(4l).build();
        File file = new File("C:\\Users\\Miller\\Desktop\\2k壁纸\\gamersky_01origin_01_20174152144B91.jpg");
        ShopExecution shopExecution = shopService.modifyShop(shop, new FileInputStream(file), "gamersky_01origin_01_20174152144B91.jpg");
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }


    @Test
    public void ShopExecution() {

        ShopExecution se = shopService.getShopList(Shop.builder()
                .owner(PersonInfo.builder().id(1L).build())
                .area(Area.builder().id(2).build())
                .build(), 2, 2);
        System.out.println(se.getShopList().size());

    }
}
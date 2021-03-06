package com.miller.o2o.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miller.o2o.BaseTest;
import com.miller.o2o.entity.Area;
import com.miller.o2o.entity.PersonInfo;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;


    @Test
    public void insert() {
        Shop build = Shop.builder().name("小吃城").desc("我的小吃城").addr("南沣村").phone("13022996276").img("").
                priority(10).enableStatus(1).advice("无").
                area(Area.builder().id(1).build())
                .owner(PersonInfo.builder().id(1L).build()).
                shopCategory(ShopCategory.builder().id(1l).build()).build();

        int insert = shopDao.insert(build);
        assertEquals(1, insert);

    }

    @Test
    @Ignore
    public void update() {
        Shop build = Shop.builder().id(4l).name("2").build();
        int update = shopDao.update(build);
        assertEquals(1, update);

    }

    @Test
    public void queryById() {
        long id = 4;
        Shop shop = shopDao.queryById(id, null);
        assertEquals("2",shop.getName());
    }

    @Test
    public void queryList() {
        PageHelper.startPage(0, 5);
        List<Shop> shopList = shopDao.queryListByPage(Shop.builder()
                .owner(PersonInfo.builder().id(1L).build())
                .area(Area.builder().id(2).build())
                .build());
        PageInfo pageInfo = PageInfo.of(shopList);

    }
}
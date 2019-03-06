package com.miller.o2o.dao;

import com.miller.o2o.entity.Shop;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public interface ShopDao {

    int insert(Shop shop);

    int update(Shop shop);

    Shop queryById(long id);
}

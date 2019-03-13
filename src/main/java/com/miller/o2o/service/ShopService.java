package com.miller.o2o.service;

import com.miller.o2o.dto.ShopExecution;
import com.miller.o2o.entity.Shop;

import java.io.File;
import java.io.InputStream;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public interface ShopService {

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition 条件
     * @param pageNum 起始页
     * @param pageSize 查几条
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageNum, int pageSize);

    /**
     * 注册店铺信息，包括图片处理
     *
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     */
    ShopExecution add(Shop shop, InputStream shopImgInputStream, String fileName);

    /**
     * 通过店铺id获取店铺信息
     *
     * @param id
     * @return
     */
    Shop getById(long id);


    /**
     * 更新店铺信息，包括对图片的处理
     *
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName);
}

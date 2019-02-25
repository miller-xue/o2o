package com.miller.o2o.service.impl;

import com.miller.o2o.dao.ShopDao;
import com.miller.o2o.dto.ShopExecution;
import com.miller.o2o.entity.Shop;
import com.miller.o2o.enums.ShopStateEnum;
import com.miller.o2o.exceptions.ShopOperationException;
import com.miller.o2o.service.ShopService;
import com.miller.o2o.util.ImageUtil;
import com.miller.o2o.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ShopExecution add(Shop shop, InputStream shopImgInputStream, String shopImgFileName) {
        // 1.参数校验
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            // 2.初始值补充
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            // 3.添加店铺信息
            int effectedNum = shopDao.insert(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            }else {
                if (shopImgInputStream != null) {
                    // 4.储存图片
                    try {
                        addShopImg(shop, shopImgInputStream, shopImgFileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    // 5.更新店铺图片地址
                    effectedNum = shopDao.update(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String shopImgFileName) throws IOException {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,shopImgFileName, dest);
        shop.setImg(shopImgAddr);

    }
}

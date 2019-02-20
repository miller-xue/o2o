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

import java.io.File;
import java.io.IOException;

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
    public ShopExecution add(Shop shop, File shopImg) {
        // 1.参数校验
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            if (shopImg != null) {
                // 2.储存图片
                try {
                    addShopImg(shop, shopImg);
                } catch (Exception e) {
                    throw new ShopOperationException("addShopImg error:" + e.getMessage());
                }
            }
            // 3.补充数据
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            int effectedNum = shopDao.insert(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            }
        } catch (Exception e) {
            throw new ShopOperationException("add error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, File shopImg) throws IOException {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setImg(shopImgAddr);

    }
}

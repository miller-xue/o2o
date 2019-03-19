package com.miller.o2o.service.impl;

import com.miller.o2o.dao.ProductDao;
import com.miller.o2o.dto.ImageHolder;
import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.entity.ProductImg;
import com.miller.o2o.enums.ProductStateEnum;
import com.miller.o2o.exceptions.ProductOperationException;
import com.miller.o2o.service.ProductImgService;
import com.miller.o2o.service.ProductService;
import com.miller.o2o.util.ImageUtil;
import com.miller.o2o.util.PathUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by miller on 2019/3/18
 * 店铺
 * @author Miller
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgService productImgService;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductExecution add(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        // 1.处理缩略图，获取缩略图相对路径并赋值给product
        // 2.往tb_product写入商品信息,获取productId
        // 3.结合productId批量处理商品详情图
        // 4.将商品详情图插入tb_product_img表
        if (product != null && product.getShop() != null && product.getShop().getId() != null) {
            // 补充数据
            product.setEnableStatus(1);
            // 缩略图不为空则添加
            if (thumbnail != null) {
                try {
                    addThumbnail(product, thumbnail);
                } catch (IOException e) {
                    throw new ProductOperationException("添加缩略图失败" + e.getMessage());
                }
            }
            // 创建商品信息
            int effectenNum = productDao.insert(product);
            if (effectenNum <= 0) {
                throw new ProductOperationException("创建商品失败");
            }
            // 商品详情图
            addProductImgList(product, productImgList);
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }
        return new ProductExecution(ProductStateEnum.EMPTY);
    }

    private void addProductImgList(final Product product, List<ImageHolder> productImgList) {
        if (CollectionUtils.isEmpty(productImgList)) {
            return;
        }
        final String dest = PathUtil.getShopImagePath(product.getShop().getId());
        List<ProductImg> productImgs = productImgList.stream().map((ImageHolder i) -> {
            try {
                String imgAddr = ImageUtil.generateNormalImg(i, dest);
                return ProductImg.builder().addr(imgAddr).productId(product.getId()).build();
            } catch (IOException e) {
                throw new ProductOperationException("创建商品详情图失败");
            }
        }).collect(Collectors.toList());
        int effectedNum = productImgService.batchAdd(productImgs);
        if (effectedNum <= 0) {
            throw new ProductOperationException("创建商品详情图失败");
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) throws IOException {
        String dest = PathUtil.getShopImagePath(product.getShop().getId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}

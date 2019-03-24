package com.miller.o2o.service;

import com.miller.o2o.dto.ImageHolder;
import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
public interface ProductService {


    ProductExecution getList(Product condition, int pageNum, int pageSize);

    /**
     * 添加商品信息以及图片处理
     *
     * @param product        商品对象
     * @param thumbnail      缩略图
     * @param productImgList 商品子图
     * @return
     * @throws ProductOperationException
     */
    ProductExecution add(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);

    /**
     * 根据商品id查询商品
     * @param id
     * @return
     */
    Product getById(long id);

    /**
     * 修改商品信息以及图片处理
     *
     * @param product        商品对象
     * @param thumbnail      缩略图
     * @param productImgList 商品子图
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modify(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);

    boolean setProductCategoryToNull(long productCategoryId);

}

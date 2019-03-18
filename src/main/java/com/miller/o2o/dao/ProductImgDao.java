package com.miller.o2o.dao;

import com.miller.o2o.entity.ProductImg;

import java.util.List;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
public interface ProductImgDao {

    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsert(List<ProductImg> productImgList);

    List<ProductImg> selectList(long productId);

    int deleteByProductId(long productId);
}

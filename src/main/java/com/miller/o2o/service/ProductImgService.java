package com.miller.o2o.service;

import com.miller.o2o.entity.Product;
import com.miller.o2o.entity.ProductImg;

import java.util.List;

/**
 * Created by miller on 2019/3/19
 *
 * @author Miller
 */
public interface ProductImgService {
    boolean batchAdd(List<ProductImg> imgList);

    boolean deleteByProductId(Product product);
}

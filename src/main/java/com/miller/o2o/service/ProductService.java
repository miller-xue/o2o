package com.miller.o2o.service;

import com.miller.o2o.dto.ProductExecution;
import com.miller.o2o.entity.Product;
import com.miller.o2o.exceptions.ProductOperationException;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
public interface ProductService {

    /**
     * 添加商品信息以及图片处理
     * @param product
     * @return
     * @throws ProductOperationException
     */
    ProductExecution add(Product product)throws ProductOperationException
}

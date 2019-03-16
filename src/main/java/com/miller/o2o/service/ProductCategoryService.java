package com.miller.o2o.service;

import com.miller.o2o.dto.ProductCategoryExecution;
import com.miller.o2o.entity.ProductCategory;

import java.util.List;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
public interface ProductCategoryService {

    List<ProductCategory> getList(long shopId);

    ProductCategoryExecution batchAdd(List<ProductCategory> list);
}

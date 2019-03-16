package com.miller.o2o.service.impl;

import com.miller.o2o.dao.ProductCategoryDao;
import com.miller.o2o.dto.ProductCategoryExecution;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.enums.ProductCategoryStateEnum;
import com.miller.o2o.exceptions.ProductCategoryOperationException;
import com.miller.o2o.service.ProductCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getList(long shopId) {
        return productCategoryDao.selectList(shopId);
    }

    @Override
    public ProductCategoryExecution batchAdd(List<ProductCategory> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
        int effectedNum = productCategoryDao.batchInsert(list);
        if (effectedNum <= 0) {
            throw new ProductCategoryOperationException("店铺类别创建失败");
        }
        return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
    }


}

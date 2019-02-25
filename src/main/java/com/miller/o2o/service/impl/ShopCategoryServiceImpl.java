package com.miller.o2o.service.impl;

import com.miller.o2o.dao.ShopCategoryDao;
import com.miller.o2o.entity.ShopCategory;
import com.miller.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by miller on 2019/2/25
 *
 * @author Miller
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryList(shopCategoryCondition);
    }
}

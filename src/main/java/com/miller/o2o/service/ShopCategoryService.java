package com.miller.o2o.service;

import com.miller.o2o.entity.ShopCategory;

import java.util.List;

/**
 * Created by miller on 2019/2/25
 *
 * @author Miller
 */
public interface ShopCategoryService {

    List<ShopCategory> getList(ShopCategory condition);
}

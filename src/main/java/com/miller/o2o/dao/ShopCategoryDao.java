package com.miller.o2o.dao;

import com.miller.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by miller on 2019/2/25
 *
 * @author Miller
 */
public interface ShopCategoryDao {

    List<ShopCategory> queryList(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}

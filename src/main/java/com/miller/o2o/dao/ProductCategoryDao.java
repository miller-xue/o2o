package com.miller.o2o.dao;

import com.miller.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
public interface ProductCategoryDao {

    /**
     * 查找指定店铺的所有分类
     * @param shopId
     * @return
     */
    List<ProductCategory> selectList(long shopId);

    int insert(ProductCategory productCategory);

    int update(ProductCategory productCategory);

    int batchInsert(List<ProductCategory> list);

    int delete(@Param("id") long id,@Param("shopId") long shopId);
}

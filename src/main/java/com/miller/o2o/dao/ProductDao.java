package com.miller.o2o.dao;

import com.miller.o2o.entity.Product;

import java.util.List;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
public interface ProductDao {

    int insert(Product product);

    int update(Product product);

    Product selectById(long id);

    List<Product> selectList(Product condition);

    int updateProductCategoryToNull(long productCategoryId);
}

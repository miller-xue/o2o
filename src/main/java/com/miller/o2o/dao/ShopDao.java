package com.miller.o2o.dao;

import com.miller.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public interface ShopDao {

    int insert(Shop shop);

    int update(Shop shop);

    Shop queryById(long id);

    /**
     * 分页查询店铺，可输入的条件有：店铺名(模糊)，店铺类别，区域Id,owner
     *
     * TODO 加上分页功能
     */
    List<Shop> queryList(@Param("shopCondition") Shop shopCondition,
                         @Param("rowIndex") int rowIndex,
                         @Param("pageSize") int pageSize);

    int queryListCount(@Param("shopCondition") Shop shopCondition,
                       @Param("rowIndex") int rowIndex,
                       @Param("pageSize") int pageSize);

}

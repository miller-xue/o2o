package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by miller on 2019/2/2
 */

@Getter
@Setter
public class Product {

    private Long id;

    private String name;

    private String desc;

    private String imgAddr;

    private String normalPrice;

    private String promotionPrice;


    /**
     * 权重
     */
    private Integer priority;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date lastEditTime;

    /**
     * -1不可用 0下架 1在前端展示系统展示
     */
    private Integer enableStatus;


    private List<ProductImg> productImgList;

    private ProductCategory productCategory;

    private Shop shop;


}

package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by miller on 2019/2/2
 * @author Miller
 */

@Getter
@Setter
public class Product {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 图片地址
     */
    private String imgAddr;

    /**
     * 正常价格
     */
    private String normalPrice;

    /**
     * 促销价格
     */
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


    /**
     * 商品图片
     */
    private List<ProductImg> productImgList;

    /**
     * 商品所属分类
     */
    private ProductCategory productCategory;

    /**
     * 商品所属店家
     */
    private Shop shop;


}

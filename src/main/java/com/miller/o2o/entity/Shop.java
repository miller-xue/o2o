package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 * @author Miller
 */
@Getter
@Setter
public class Shop {

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
     * 地址
     */
    private String addr;

    /**
     * 电话
     */
    private String phone;

    /**
     * 图片
     */
    private String img;

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
     * -1.不可用 0.审核中 1.可用
     */
    private Integer enableStatus;

    /**
     * 管理员提示
     */
    private String advice;

    /**
     * 店铺所属区域
     */
    private Area area;

    /**
     * 店铺归属人
     */
    private PersonInfo owner;

    /**
     * 店铺分类
     */
    private ShopCategory shopCategory;


}

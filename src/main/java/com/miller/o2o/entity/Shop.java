package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 */
@Getter
@Setter
public class Shop {

    private Long id;

    private String name;

    private String desc;

    private String addr;

    private String phone;

    private String img;

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

    private String advice;

    private Area area;

    private PersonInfo owner;

    private ShopCategory shopCategory;


}

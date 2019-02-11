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
public class ProductImg {

    private Long id;

    /**
     * 图片归属商品ID
     */
    private Long productId;

    /**
     * 地址
     */
    private String addr;

    /**
     * 描述
     */
    private String desc;

    /**
     * 权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;



}

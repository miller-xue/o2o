package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 */
@Getter
@Setter
public class ProductCategory {

    private Long id;

    private Long shopId;

    private String name;

    /**
     * 权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

}

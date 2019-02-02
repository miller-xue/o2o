package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 */
@Getter
@Setter
public class ProductImg {
    private Long id;

    private String addr;

    private String desc;

    /**
     * 权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

    private Long productId;

}

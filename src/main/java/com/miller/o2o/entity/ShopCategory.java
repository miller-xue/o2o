package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 */
@Getter
@Setter
public class ShopCategory {

    private Long id;

    private String name;

    private String desc;

    private String img;

    private Integer priority;

    private Date createTime;

    private Date laseEditTime;

    private ShopCategory praent;
}

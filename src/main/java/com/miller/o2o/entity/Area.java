package com.miller.o2o.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by miller on 2019/1/31
 * @author Miller
 *
 * 基本类型会产生默认值，引用类型为null
 */
@Getter
@Setter
public class Area {

    /**
     * ID。
     */
    private Integer id;

    /**
     * 名称。
     */
    private String name;

    /**
     * 权重。
     */
    private Integer priority;

    /**
     * 创建时间。
     */
    private Date createTime;

    /**
     * 更新时间。
     */
    private Date lastEditTime;
}

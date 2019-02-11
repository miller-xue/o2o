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
public class HeadLine {

    /**
     * ID
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 连接
     */
    private String link;

    /**
     * 图片
     */
    private String img;

    /**
     * 权重
     */
    private Integer priority;

    /**
     *  0.不可用 1.可用
     */
    private Integer enableStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date laseEditTime;
}

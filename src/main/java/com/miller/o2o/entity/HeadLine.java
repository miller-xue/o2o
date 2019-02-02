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

    private Integer id;

    private String name;

    private String link;

    private String img;

    private Integer priority;
    /**
     *  0.不可用 1.可用
     */
    private Integer enableStatus;

    private Date createTime;

    private Date laseEditTime;
}

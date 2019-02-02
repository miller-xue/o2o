package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 */
@Getter
@Setter
public class LocalAuth {

    private Long id;

    private String username;

    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date lastEditTime;

    private PersonInfo personInfo;

}

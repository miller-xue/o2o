package com.miller.o2o.entity;

import lombok.*;

import java.util.Date;

/**
 * Created by miller on 2019/1/31
 *
 * @author Miller
 */


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PersonInfo {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 头像
     */
    private String profileImg;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 启用状态
     */
    private Integer enableStatus;

    /**
     * 1.顾客  2.店家  3.超级管理员
     */
    private Integer type;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date lastEditTime;
}




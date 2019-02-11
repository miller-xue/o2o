package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 * @author Miller
 * 微信授权
 */
@Getter
@Setter
public class WechatAuth {

    private Long id;

    /**
     * 微信openId
     */
    private String openId;


    private Date createTime;

    /**
     * 关联的人员对象
     */
    private PersonInfo personInfo;
}

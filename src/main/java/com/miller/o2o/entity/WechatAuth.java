package com.miller.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by miller on 2019/2/2
 */
@Getter
@Setter
public class WechatAuth {

    private Long id;

    private String openId;


    private Date createTime;

    private PersonInfo personInfo;
}

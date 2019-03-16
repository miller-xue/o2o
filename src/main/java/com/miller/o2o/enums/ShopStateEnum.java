package com.miller.o2o.enums;

import com.miller.o2o.common.BaseState;
import lombok.Getter;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
@Getter
public enum ShopStateEnum implements BaseState {

    CHECK(0, "审核中"),
    OFF_LINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_ID(-1002, "id为空"),
    NULL_SHOP(-1003, "shop信息为空"),
    NULL_SHOP_IMG(-1004, "shopImg为空"),
    ;

    private int state;

    private String stateInfo;

    ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
}

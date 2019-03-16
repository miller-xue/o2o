package com.miller.o2o.enums;

import com.miller.o2o.common.BaseState;
import lombok.Getter;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
@Getter
public enum  ProductCategoryStateEnum implements BaseState {
    INNER_ERROR(-1001, "内部系统错误"),
    SUCCESS(1, "操作成功"),
    EMPTY_LIST(1, "空集合"),
    ;
    /**
     * 状态码
     */
    private int state;

    /**
     * 状态信息
     */
    private String stateInfo;

    ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

}

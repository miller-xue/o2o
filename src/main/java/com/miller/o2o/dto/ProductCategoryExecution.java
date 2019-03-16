package com.miller.o2o.dto;

import com.miller.o2o.common.BaseState;
import com.miller.o2o.entity.ProductCategory;
import com.miller.o2o.enums.ProductCategoryStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by miller on 2019/3/16
 *
 * @author Miller
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryExecution {

    /**
     * 结果状态
     */
    private int state;

    /**
     * 状态标识
     */
    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution(BaseState stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    public ProductCategoryExecution(BaseState stateEnum,List<ProductCategory> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }
}

package com.miller.o2o.dto;

import com.miller.o2o.entity.Product;
import com.miller.o2o.enums.ProductStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
@Getter
@Setter
public class ProductExecution {
    /**
     * 结果状态
     */
    private int state;

    /**
     * 状态标识
     */
    private String stateInfo;

    /**
     * 店铺数量
     */
    private long count;

    /**
     * 操作的product(增删改查用到)
     */
    private Product product;

    /**
     * product列表(查询列表时用到)
     */
    private List<Product> productList;

    private ProductExecution() {
    }

    /**
     * 操作成功构造器
     * @param stateEnum
     * @param productList
     */
    public ProductExecution(ProductStateEnum stateEnum,List<Product> productList) {
        this(stateEnum);
        this.productList = productList;
    }

    /**
     * 操作成功构造器
     * @param stateEnum
     * @param product
     */
    public ProductExecution(ProductStateEnum stateEnum,Product product) {
        this(stateEnum);
        this.product = product;
    }

    /**
     * 操作失败构造器
     * @param stateEnum
     */
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}

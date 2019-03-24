package com.miller.o2o.dto;

import com.miller.o2o.entity.Shop;
import com.miller.o2o.enums.ShopStateEnum;
import lombok.*;

import java.util.List;

/**
 * Created by miller on 2019/2/17
 * TODO 抽取Execution 和Ajax 进行封装
 * @author Miller
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopExecution {
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
     * 操作的shop(增删改查用到)
     */
    private Shop shop;

    /**
     * shop列表(查询列表时用到)
     */
    private List<Shop> shopList;

    /**
     * 店铺操作失败的时候使用的构造器
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功使用的构造器
     * @param stateEnum
     * @param shop 店铺对象
     */
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     *
     * @param stateEnum
     * @param shopList 店铺列表
     */
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }
}

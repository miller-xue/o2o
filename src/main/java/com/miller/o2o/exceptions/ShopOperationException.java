package com.miller.o2o.exceptions;

/**
 * Created by miller on 2019/2/17
 * 店铺操作异常
 *
 * @author Miller
 */
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = 4190095821813362780L;

    public ShopOperationException(String message) {
        super(message);
    }
}

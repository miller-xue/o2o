package com.miller.o2o.exceptions;

/**
 * Created by miller on 2019/2/17
 * 商品分类操作异常
 *
 * @author Miller
 */
public class ProductCategoryOperationException extends BusinessException {


    private static final long serialVersionUID = -6516611198805792011L;

    public ProductCategoryOperationException(String message) {
        super(message);
    }
}

package com.miller.o2o.exceptions;

/**
 * Created by miller on 2019/3/18
 *
 * @author Miller
 */
public class ProductOperationException extends BusinessException {

    private static final long serialVersionUID = -5436731682389280536L;

    public ProductOperationException(String message) {
        super(message);
    }
}

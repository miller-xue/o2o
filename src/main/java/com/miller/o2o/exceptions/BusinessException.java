package com.miller.o2o.exceptions;

/**
 * Created by miller on 2019/3/9
 *
 * @author Miller
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -1826687073146573759L;

    public BusinessException(String message) {
        super(message);
    }
}

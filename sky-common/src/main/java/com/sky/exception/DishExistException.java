package com.sky.exception;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 15:05
 **/


public class DishExistException extends BaseException {
    public DishExistException() {
    }

    public DishExistException(String msg) {
        super(msg);
    }
}

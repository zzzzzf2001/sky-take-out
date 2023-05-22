package com.sky.exception;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/22 14:27
 **/


public class AccountExistException extends BaseException{
    public AccountExistException() {
    }

    public AccountExistException(String msg) {
        super(msg);
    }
}

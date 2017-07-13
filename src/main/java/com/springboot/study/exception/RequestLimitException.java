package com.springboot.study.exception;

/**
 * Created by ps on 2017/7/7.
 */
public class RequestLimitException extends Exception {

    private static final long serialVersionUID = 1L;

    public RequestLimitException() {
        super("HTTP请求超过限制");
    }

    public RequestLimitException(String message) {
        super(message);
    }
}

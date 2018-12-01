package com.qingqq.redlock.exception;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO 获取锁异常类
 */
public class UnableToAquireLockException extends RuntimeException{
    private static final long serialVersionUID = -3183786642714735423L;


    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}

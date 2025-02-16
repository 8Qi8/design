package com.yyq.common.exception;

/**
 * 密码错误异常
 */
public class VerificationCodeErrorException extends BaseException {

    public VerificationCodeErrorException() {
    }

    public VerificationCodeErrorException(String msg) {
        super(msg);
    }

}

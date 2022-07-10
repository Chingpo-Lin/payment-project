package com.example.paymentdemo.exception;

/**
 * custom exception
 */
public class GlobalException extends RuntimeException {

    /**
     * status code
     */
    private Integer code;

    /**
     * error msg
     */
    private String msg;

    public GlobalException() {
        super();
    }

    public GlobalException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

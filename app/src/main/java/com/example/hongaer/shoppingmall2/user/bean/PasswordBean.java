package com.example.hongaer.shoppingmall2.user.bean;

/**
 * Created by hongaer on 2017/8/3.
 */

public class PasswordBean {

    /**
     * status : 0
     * message : 原始密码输入错误
     */

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PasswordBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}

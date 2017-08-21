package com.example.hongaer.shoppingmall2.user.bean;

/**
 * Created by hongaer on 2017/7/30.
 */

public class LoginBean {


    /**
     * status : 1
     * username : shen
     * head_ico : null
     * true_name : shen
     * telephone : 02500000000
     * mobile : 13000000000
     * qq : 123456789
     * sex : 1
     * email : @sina.com
     */

    private int status;
    private String username;
    private Object head_ico;
    private String true_name;
    private String telephone;
    private String mobile;
    private String qq;
    private String sex;
    private String email;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usernam) {
        this.username = usernam;
    }

    public Object getHead_ico() {
        return head_ico;
    }

    public void setHead_ico(Object head_ico) {
        this.head_ico = head_ico;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "status=" + status +
                ", username='" + username + '\'' +
                ", head_ico=" + head_ico +
                ", true_name='" + true_name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", qq='" + qq + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

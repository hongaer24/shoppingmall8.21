package com.example.hongaer.shoppingmall2.home.bean;

import java.io.Serializable;

/**
 * 商品对象
 */

public class GoodsBean implements Serializable {
    private String name;
    private String cover_price;
    private String figure;
    private String product_id;
    private int number=1;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    String addressID;
    //详细地址
    String address;

    //电话
    String mobile;

    //收货人姓名
    String consignee;


    //是否默认地址
    String isDefault;

    String fullAddress;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected =true;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "name='" + name + '\'' +
                ", cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", addressID='" + addressID + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", consignee='" + consignee + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}

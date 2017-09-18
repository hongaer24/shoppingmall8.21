package com.example.hongaer.shoppingmall2.home.bean;

import java.io.Serializable;

/**
 * 商品对象
 */

public class GoodsBean implements Serializable {

    //店铺名称
    private String sellername;
    //商品名称
    private String name;
    private String cover_price;
    private String figure;
    private String product_id;
    private int number = 1;
    private int goods_id;

    //电话
    private String mobile;

    //收货人姓名
    private String consignee;

    //是否默认地址
    private  String isDefault;

    private String fullAddress;

    private String addressID;
    //详细地址
    private String address;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    private boolean isSelected ;
    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }



    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

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
       this.isSelected = selected;
    }



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

    @Override
    public String toString() {
        return "GoodsBean{" +
                "sellername='" + sellername + '\'' +
                ", name='" + name + '\'' +
                ", cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", goods_id=" + goods_id +
                ", mobile='" + mobile + '\'' +
                ", consignee='" + consignee + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", addressID='" + addressID + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}

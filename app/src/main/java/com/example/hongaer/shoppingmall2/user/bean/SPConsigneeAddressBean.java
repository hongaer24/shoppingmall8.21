package com.example.hongaer.shoppingmall2.user.bean;

import java.io.Serializable;

/**
 * Created by hongaer on 2017/8/18.
 */

public class SPConsigneeAddressBean implements Serializable{
    //地址ID
    String addressID;

    String userID;
    //收货人姓名
    String consignee;

    //国家号码
    String country;

    //省份
    String province;

    //城市号码
    String city;


    //区号码
    String district;

    //镇/街道
    String town;

    //详细地址
    String address;

    //电话
    String mobile;

    //是否默认地址
    String isDefault;

    String fullAddress;

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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

    @Override
    public String toString() {
        return "SPConsigneeAddressBean{" +
                "addressID='" + addressID + '\'' +
                ", userID='" + userID + '\'' +
                ", consignee='" + consignee + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}

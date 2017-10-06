package com.example.hongaer.shoppingmall2.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongaer on 2017/10/1.
 */

public class AddressDatasBean implements Serializable {


    /**
     * status : 1000
     * message : 获取成功
     * address : [{"id":"18","user_id":"8","accept_name":"14","zip":"","telphone":"","country":null,"province":"0","city":"0","area":"0","address":"","mobile":"","is_default":"0"},{"id":"19","user_id":"8","accept_name":"45","zip":"","telphone":"","country":null,"province":"0","city":"0","area":"0","address":"","mobile":"44","is_default":"0"},{"id":"21","user_id":"8","accept_name":"","zip":"","telphone":"","country":null,"province":"0","city":"0","area":"0","address":"","mobile":"77","is_default":"0"},{"id":"22","user_id":"8","accept_name":"88","zip":"","telphone":"","country":null,"province":"0","city":"0","area":"0","address":"","mobile":"88","is_default":"0"},{"id":"23","user_id":"8","accept_name":"22","zip":"","telphone":"","country":null,"province":"0","city":"0","area":"0","address":"","mobile":"22","is_default":"0"},{"id":"24","user_id":"8","accept_name":"99","zip":"","telphone":"","country":null,"province":"11","city":"11","area":"11","address":"","mobile":"99","is_default":"0"}]
     */

    private int status;
    private String message;
    private List<AddressBean> address;

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

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * id : 18
         * user_id : 8
         * accept_name : 14
         * zip :
         * telphone :
         * country : null
         * province : 0
         * city : 0
         * area : 0
         * address :
         * mobile :
         * is_default : 0
         */

        private String id;
        private String user_id;
        private String accept_name;
        private String zip;
        private String telphone;
        private Object country;
        private String province;
        private String city;
        private String area;
        private String address;
        private String mobile;
        private String is_default;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAccept_name() {
            return accept_name;
        }

        public void setAccept_name(String accept_name) {
            this.accept_name = accept_name;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
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

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        @Override
        public String toString() {
            return "AddressBean{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", accept_name='" + accept_name + '\'' +
                    ", zip='" + zip + '\'' +
                    ", telphone='" + telphone + '\'' +
                    ", country=" + country +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", address='" + address + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", is_default='" + is_default + '\'' +
                    '}';
        }
    }


}

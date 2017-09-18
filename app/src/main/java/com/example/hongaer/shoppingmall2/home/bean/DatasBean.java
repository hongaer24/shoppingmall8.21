package com.example.hongaer.shoppingmall2.home.bean;

import java.util.List;

/**
 * Created by hongaer on 2017/8/18.
 */

public class DatasBean {

    /**
     * data : [{"id":218,"type":"goods","goods_id":218,"count":1,"img":"runtime/_thumb/upload/2016/01/22/120_120_568cccfdN6f817ec5.jpg","name":"法国进口红酒 塞莱斯城堡干红葡萄酒 整箱装 750ml*6瓶","sell_price":"238.00"}]
     * count : 1
     * sum : 238
     */

    private int count;
    private int sum;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 218
         * type : goods
         * goods_id : 218
         * count : 1
         * img : runtime/_thumb/upload/2016/01/22/120_120_568cccfdN6f817ec5.jpg
         * name : 法国进口红酒 塞莱斯城堡干红葡萄酒 整箱装 750ml*6瓶
         * sell_price : 238.00
         */

        private int id;
        private String type;
        private int goods_id;
        private int count;
        private String img;
        private String name;
        private String sell_price;

        public String getSellprice() {
            return sellprice;
        }

        public void setSellprice(String sellprice) {
            this.sellprice = sellprice;
        }

        private String sellprice;
        private String sellername;

        public String getSellername() {
            return sellername;
        }

        public void setSellername(String sellername) {
            this.sellername = sellername;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }
    }
}

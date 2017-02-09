package com.mcxtzhang.rxjava2demo.retrofit.model.bf;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/9.
 * History:
 */

public class PostBean {
    /**
     * address : gg
     * address_id : 721866
     * consignee : gghh
     * consignee_tel : 18616320845
     * floor_id : 98448
     * gender : 1
     * notice_way : 1
     * order_list : [{"comment":"","delivery_date":"20161123","goods":[{"goods_sale_id":"54","number":"3","price":0.02},{"goods_sale_id":"45","number":"7","price":0.1}]}]
     * payway : 3
     * user_coupon_id : -1
     */

    @SerializedName("address")
    private String address;
    @SerializedName("address_id")
    private String addressId;
    @SerializedName("consignee")
    private String consignee;
    @SerializedName("consignee_tel")
    private String consigneeTel;
    @SerializedName("floor_id")
    private String floorId;
    @SerializedName("gender")
    private int gender;
    @SerializedName("notice_way")
    private int noticeWay;
    @SerializedName("payway")
    private int payway;
    @SerializedName("user_coupon_id")
    private String userCouponId;
    @SerializedName("order_list")
    private List<OrderListBean> orderList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getNoticeWay() {
        return noticeWay;
    }

    public void setNoticeWay(int noticeWay) {
        this.noticeWay = noticeWay;
    }

    public int getPayway() {
        return payway;
    }

    public void setPayway(int payway) {
        this.payway = payway;
    }

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * comment :
         * delivery_date : 20161123
         * goods : [{"goods_sale_id":"54","number":"3","price":0.02},{"goods_sale_id":"45","number":"7","price":0.1}]
         */

        @SerializedName("comment")
        private String comment;
        @SerializedName("delivery_date")
        private String deliveryDate;
        @SerializedName("goods")
        private List<GoodsBean> goods;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goods_sale_id : 54
             * number : 3
             * price : 0.02
             */

            @SerializedName("goods_sale_id")
            private String goodsSaleId;
            @SerializedName("number")
            private String number;
            @SerializedName("price")
            private double price;

            public String getGoodsSaleId() {
                return goodsSaleId;
            }

            public void setGoodsSaleId(String goodsSaleId) {
                this.goodsSaleId = goodsSaleId;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }
}

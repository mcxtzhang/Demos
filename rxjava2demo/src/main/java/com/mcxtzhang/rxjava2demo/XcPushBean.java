package com.mcxtzhang.rxjava2demo;

import com.google.gson.annotations.SerializedName;


/**
 * 介绍：校超PushBean
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/9/5.
 */
public class XcPushBean {
    /**
     * messageType : 1
     * sysCode : 10
     * data : {"shortOrderId":"14","orderId":"11170906638560001","content":"你有新的俺来也订单，订单取货号#14"}
     */

    @SerializedName("messageType")
    private int messageType;
    @SerializedName("sysCode")
    private int sysCode;
    @SerializedName("data")
    private DataBean data;

    //1 新订单通知

    public boolean isNewOrder() {
        return messageType == 1;
    }


    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getSysCode() {
        return sysCode;
    }

    public void setSysCode(int sysCode) {
        this.sysCode = sysCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shortOrderId : 14
         * orderId : 11170906638560001
         * content : 你有新的俺来也订单，订单取货号#14
         */

        @SerializedName("shortOrderId")
        private String shortOrderId;
        @SerializedName("orderId")
        private String orderId;
        @SerializedName("content")
        private String content;

        public String getShortOrderId() {
            return shortOrderId;
        }

        public void setShortOrderId(String shortOrderId) {
            this.shortOrderId = shortOrderId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

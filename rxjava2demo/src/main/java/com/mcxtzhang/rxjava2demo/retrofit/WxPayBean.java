package com.mcxtzhang.rxjava2demo.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * project Android_Aly
 *
 * @author hewei
 * @version 16/4/19
 */
public class WxPayBean {
    @Override
    public String toString() {
        return "WxPayBean{" +
                "prepayId='" + prepayId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", msg='" + msg + '\'' +
                ", xpackage='" + xpackage + '\'' +
                ", rndStr='" + rndStr + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    @SerializedName("prepayId")
    public String prepayId;
    @SerializedName("orderId")
    public String orderId; // 钱包的交易号
    @SerializedName("msg")
    public String msg;
    @SerializedName("xpackage")
    public String xpackage;
    @SerializedName("rndStr")
    public String rndStr;
    @SerializedName("timeStamp")
    public String timeStamp;
    @SerializedName("sign")
    public String sign;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getXpackage() {
        return xpackage;
    }

    public void setXpackage(String xpackage) {
        this.xpackage = xpackage;
    }

    public String getRndStr() {
        return rndStr;
    }

    public void setRndStr(String rndStr) {
        this.rndStr = rndStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

package mcxtzhang.recyclerviewdemo.expand;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Intro: 店铺 简情
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/4/19.
 * History:
 */

public class MoonStoreInfo {

    /**
     * image_url : http://pic.anlaiye.com.cn/df18e107265849cf9445ae782a85f4bc_1242x1242.png
     * work_status : 营业中
     * earth_name : 王小二
     * goods_num : 8
     * praise_num : 2
     * sales_volume : 100
     */
    public static List<MoonStoreInfo> fakerDatas() {
        List<MoonStoreInfo> datas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            datas.add(fakerData());
        }
        return datas;
    }

    public static MoonStoreInfo fakerData() {
        MoonStoreInfo moonStoreInfo;
        moonStoreInfo = new MoonStoreInfo();
        moonStoreInfo.imageUrl = "http://pic.anlaiye.com.cn/df18e107265849cf9445ae782a85f4bc_1242x1242.png";
        moonStoreInfo.workStatus = 1;
        moonStoreInfo.workStatusDesc = "营业中";
        moonStoreInfo.earthName = "张旭童";
        moonStoreInfo.goodsNum = 8;
        moonStoreInfo.praiseNum = 2;
        moonStoreInfo.salesVolume = 100;
        moonStoreInfo.storeId = 61289;
        return moonStoreInfo;
    }

    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("earth_name")
    private String earthName;
    @SerializedName("goods_num")
    private int goodsNum;
    @SerializedName("praise_num")
    private int praiseNum;
    @SerializedName("sales_volume")
    private int salesVolume;
    /**
     * work_status : 1
     * work_status_desc : 营业中
     * store_id : 61289
     */

    @SerializedName("work_status")
    private int workStatus;
    @SerializedName("work_status_desc")
    private String workStatusDesc;
    @SerializedName("store_id")
    private int storeId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getEarthName() {
        return earthName;
    }

    public void setEarthName(String earthName) {
        this.earthName = earthName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkStatusDesc() {
        return workStatusDesc;
    }

    public void setWorkStatusDesc(String workStatusDesc) {
        this.workStatusDesc = workStatusDesc;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}

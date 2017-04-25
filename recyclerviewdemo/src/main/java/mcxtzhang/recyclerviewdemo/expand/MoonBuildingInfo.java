package mcxtzhang.recyclerviewdemo.expand;

import java.util.ArrayList;
import java.util.List;

/**
 * Intro: 月光宝盒 楼栋信息
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/4/19.
 * History:
 */

public class MoonBuildingInfo {

    /**
     * build_id : 1
     * bulid_name : 第一公寓
     */

    public static List<MoonBuildingInfo> fakerDatas() {
        List<MoonBuildingInfo> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(fakerData());
        }
        return list;
    }

    public static MoonBuildingInfo fakerData() {
        MoonBuildingInfo moonBuildingInfo = new MoonBuildingInfo();
        moonBuildingInfo.buildId = 1;
        moonBuildingInfo.bulidName = "第一店铺";
        moonBuildingInfo.setStoreList(MoonStoreInfo.fakerDatas());
        return moonBuildingInfo;
    }

    //默认是展开的 默认是false
    private boolean isShrink;

    private int buildId;
    private String bulidName;

    private List<MoonStoreInfo> storeList;

    public boolean isShrink() {
        return isShrink;
    }

    public MoonBuildingInfo setShrink(boolean shrink) {
        isShrink = shrink;
        return this;
    }

    public List<MoonStoreInfo> getStoreList() {
        return storeList;
    }

    public MoonBuildingInfo setStoreList(List<MoonStoreInfo> storeList) {
        this.storeList = storeList;
        return this;
    }

    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public String getBulidName() {
        return bulidName;
    }

    public void setBulidName(String bulidName) {
        this.bulidName = bulidName;
    }
}

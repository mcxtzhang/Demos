package mcxtzhang.recyclerviewdemo.nestrv;

import com.mcxtzhang.zxtcommonlib.databinding.base.mul.IBaseMulInterface;

import mcxtzhang.recyclerviewdemo.R;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class TestBean implements IBaseMulInterface {
    private String name;
    private String url;
    private int type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TestBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public int getItemLayoutId() {
        switch (type) {
            case 0:
                return R.layout.item_nest_rv_1;
            default:
                return 0;
        }
    }
}

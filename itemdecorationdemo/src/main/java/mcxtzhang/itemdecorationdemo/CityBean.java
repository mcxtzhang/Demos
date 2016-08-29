package mcxtzhang.itemdecorationdemo;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class CityBean {
    private String tag;//首字母
    private String city;//城市名字
    private String pyCity;//城市的拼音

    public CityBean() {
    }

    public CityBean(String tag, String city, String pyCity) {
        this.tag = tag;
        this.city = city;
        this.pyCity = pyCity;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPyCity() {
        return pyCity;
    }

    public void setPyCity(String pyCity) {
        this.pyCity = pyCity;
    }
}

package mcxtzhang.zandemo;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class ZanBean {
    private boolean isZaned;//是否点过赞
    private int zanCount;//赞的数量

    public ZanBean(boolean isZaned, int zanCount) {
        this.isZaned = isZaned;
        this.zanCount = zanCount;
    }

    public boolean isZaned() {
        return isZaned;
    }

    public void setZaned(boolean zaned) {
        isZaned = zaned;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }
}

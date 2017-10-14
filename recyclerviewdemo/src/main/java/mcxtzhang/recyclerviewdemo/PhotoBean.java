package mcxtzhang.recyclerviewdemo;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.commonadapter.rv.mul.IMulTypeHelper;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class PhotoBean implements IMulTypeHelper {
    private String name;
    private String url;
    private boolean isTitle;

    public boolean isTitle() {
        return isTitle;
    }

    public PhotoBean setTitle(boolean title) {
        isTitle = title;
        return this;
    }

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

    public PhotoBean(boolean isTitle, String url, String name) {
        this.name = name;
        this.url = url;
        this.isTitle = isTitle;
    }

    @Override
    public int getItemLayoutId() {
        return isTitle ? R.layout.item_photo_title : R.layout.item_photo;
    }

    @Override
    public void onBind(ViewHolder holder) {
        if (isTitle) {
            holder.setText(R.id.tvTitle, name);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(url).into((ImageView) holder.getView(R.id.iv));
        }
    }
}

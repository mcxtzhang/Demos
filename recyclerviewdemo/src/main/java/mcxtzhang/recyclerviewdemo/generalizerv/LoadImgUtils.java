package mcxtzhang.recyclerviewdemo.generalizerv;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import mcxtzhang.recyclerviewdemo.R;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/31.
 */

public class LoadImgUtils {
    @BindingAdapter("url")
    public static void LoadImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).error(R.mipmap.ic_launcher).into(imageView);
    }
}

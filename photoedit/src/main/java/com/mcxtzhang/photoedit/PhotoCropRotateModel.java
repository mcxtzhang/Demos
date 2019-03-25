package com.mcxtzhang.photoedit;

import java.io.Serializable;

/**
 * Created by zhangxutong on 2019/3/25.
 */

public class PhotoCropRotateModel implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;

    public float rotate;
    public int cropRate;

    public PhotoCropRotateModel(int x, int y, int width, int height, float rotate, int cropRate) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotate = rotate;
        this.cropRate = cropRate;
    }
}

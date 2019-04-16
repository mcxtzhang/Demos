package com.mcxtzhang.photoedit.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.mcxtzhang.photoedit.widget.UGCPhotoCropRotateModel;


/**
 * Created by zhangxutong on 2019/3/26.
 */

public class CropRotatePhotoUtil {
    public static Bitmap transform(Bitmap originBitmap, UGCPhotoCropRotateModel photoCropRotateModel) {
        if (originBitmap == null || photoCropRotateModel == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        double rotate = photoCropRotateModel.rotate;
        if (rotate == 0) {
        } else if (rotate == -90) {
            //顺时针90度
            matrix.postRotate(90);
        } else if (rotate == 180) {
            matrix.postRotate(180);
        } else if (rotate == 90) {
            matrix.postRotate(270);
        }
        return Bitmap.createBitmap(originBitmap,
                photoCropRotateModel.x,
                photoCropRotateModel.y,
                photoCropRotateModel.width,
                photoCropRotateModel.height,
                matrix, true);
    }
}

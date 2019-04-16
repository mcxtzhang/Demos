package com.mcxtzhang.photoedit.widget;

import android.os.Parcel;
import android.os.Parcelable;

public class UGCPhotoCropRotateModel implements Parcelable {
    /*
      name:UGCPhotoCropRotateModel_x
      type:int

    */
    public int x;
    /*
      name:UGCPhotoCropRotateModel_y
      type:int

    */
    public int y;
    /*
      name:UGCPhotoCropRotateModel_width
      type:int

    */
    public int width;
    /*
      name:UGCPhotoCropRotateModel_height
      type:int

    */
    public int height;
    /*
      name:UGCPhotoCropRotateModel_rotate
      type:double

    */
    public double rotate;
    /*
      name:UGCPhotoCropRotateModel_cropRate
      type:int

    */
    public int cropRate;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeDouble(this.rotate);
        dest.writeInt(this.cropRate);
    }

    public UGCPhotoCropRotateModel() {
    }

    protected UGCPhotoCropRotateModel(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.rotate = in.readDouble();
        this.cropRate = in.readInt();
    }

    public static final Creator<UGCPhotoCropRotateModel> CREATOR = new Creator<UGCPhotoCropRotateModel>() {
        @Override
        public UGCPhotoCropRotateModel createFromParcel(Parcel source) {
            return new UGCPhotoCropRotateModel(source);
        }

        @Override
        public UGCPhotoCropRotateModel[] newArray(int size) {
            return new UGCPhotoCropRotateModel[size];
        }
    };
}

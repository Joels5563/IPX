package com.ipx.ipx;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 自定义message传输对象
 */
public class ProjectPic implements Parcelable {
    //图片
    private Bitmap projectPic;
    //索引
    private int index;

    /**
     * 内容描述接口，基本不用管
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     * 因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入
     * 为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例
     * <p>
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(projectPic, flags);
        dest.writeInt(index);
    }

    public static final Parcelable.Creator<ProjectPic> CREATOR = new Parcelable.Creator<ProjectPic>() {
        public ProjectPic createFromParcel(Parcel in) {
            return new ProjectPic(in);
        }

        public ProjectPic[] newArray(int size) {
            return new ProjectPic[size];
        }
    };

    public ProjectPic(Parcel in) {
        //写入顺序和读取顺序需要一致
        this.projectPic = in.readParcelable(LoginActivity.class.getClassLoader());
        this.index = in.readInt();
    }

    /**
     * 默认构造方法
     */
    public ProjectPic() {
    }

    public Bitmap getProjectPic() {
        return projectPic;
    }

    public void setProjectPic(Bitmap projectPic) {
        this.projectPic = projectPic;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

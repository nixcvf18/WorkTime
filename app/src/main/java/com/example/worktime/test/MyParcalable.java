package com.example.worktime.test;

import android.os.Parcel;
import android.os.Parcelable;

public class MyParcalable implements Parcelable {

    private int myData;

    /*在指定的位置 读取值 然后 赋值给 字段myData*/
    protected MyParcalable(Parcel parcel) {
        myData = parcel.readInt();
    }


    /*将字段myData的值 写进parcel类中*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(myData);


    }

    @Override
    public int describeContents() {
        return 0;
    }



    /*new 了一个接口的匿名实现类*/
    public static final Creator<MyParcalable> CREATOR = new Creator<MyParcalable>() {
        /*接口中待实现的函数  createFromParcel(Parcel parcel)  */
        @Override
        public MyParcalable createFromParcel(Parcel parcel) {
            /*返回当前类的实例 */
            return new MyParcalable(parcel);
        }

        /*接口中待实现的函数 newArray(int size)   */
        @Override
        public MyParcalable[] newArray(int size) {

            /*返回当前类型的数组的实例 */
            return new MyParcalable[size];
        }
    };
}

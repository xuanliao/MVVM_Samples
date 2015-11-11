package com.xuanpeng.mvvmdatabinding;

import android.app.Application;

/**
 * Created by xuanpeng on 15/11/11.
 */
public class MVVMApplication extends Application {

    public static MVVMApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}

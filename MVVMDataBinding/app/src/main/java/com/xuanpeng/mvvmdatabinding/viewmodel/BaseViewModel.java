package com.xuanpeng.mvvmdatabinding.viewmodel;

import android.databinding.BaseObservable;

import com.xuanpeng.mvvmdatabinding.MVVMApplication;

/**
 * Created by xuanpeng on 15/11/11.
 */
public class BaseViewModel extends BaseObservable {

    protected String getString(int resId) {
        return MVVMApplication.INSTANCE.getString(resId);
    }
}

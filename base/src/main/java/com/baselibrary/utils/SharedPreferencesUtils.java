package com.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.baselibrary.BaseApplication;

public class SharedPreferencesUtils {

    public static String SP_DATA = "sp_data";

    public static SharedPreferencesUtils mInstance;
    public static SharedPreferences sharedPreferences;

    public static SharedPreferencesUtils getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferencesUtils.class){
                if (mInstance == null) {
                    mInstance =  new SharedPreferencesUtils();
                }
            }
        }
        return mInstance;
    }

    public SharedPreferencesUtils() {
        sharedPreferences = BaseApplication.getInstance().getSharedPreferences(SP_DATA, Context.MODE_PRIVATE);
    }

    public int getActivityId() {
        return sharedPreferences.getInt("ActivityId", 0);
    }

    public void setActivityId(int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ActivityId", value);
        editor.commit();
    }

}

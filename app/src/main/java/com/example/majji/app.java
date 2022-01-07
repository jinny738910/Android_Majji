package com.example.majji;

import android.app.Application;
import android.content.res.Resources;

public class app extends Application {
    private static app mInstance;
    private static Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        res = getResources();
    }

    public static app getInstance() {
        return mInstance;
    }

    public static Resources getRes() {
        return res;
    }

}
package com.example.android60;

import android.app.Application;

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler mCustomCrashHandler = CrashHandler.getInstance();
        mCustomCrashHandler.init(getApplicationContext());
    }

} 
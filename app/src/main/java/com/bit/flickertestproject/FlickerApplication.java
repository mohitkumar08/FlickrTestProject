package com.bit.flickertestproject;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class FlickerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }
}

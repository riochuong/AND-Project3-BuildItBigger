package com.udacity.gradle.builditbigger;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by chuondao on 4/21/17.
 */

public class JokeTellingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}

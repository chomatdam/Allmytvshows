package com.eseo.allmytvshows.managers;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Damien on 9/25/15.
 */
public class TvShowApplication extends Application {

    private static TvShowApplication sInstance;

    public static Bus bus = new Bus(ThreadEnforcer.MAIN);

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static TvShowApplication getInstance() {
        return sInstance;
    }

    public static Bus getBus() {
        return bus;
    }
}

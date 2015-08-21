package com.hornettao.commentapp;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.hornettao.locationutil.LocationHelper;
import com.hornettao.locationutil.LocationListener;

/**
 * Created by hornettao on 14-10-16.
 */

public class CommentAppApplication extends Application implements LocationListener{
    public static BDLocation lastLocation;
    private LocationHelper locationHelper;
    @Override
    public void onCreate() {
        SDKInitializer.initialize(this);
        super.onCreate();
        locationHelper = new LocationHelper();
        locationHelper.setContext(this);
        locationHelper.setLocationListener(this);
        locationHelper.start();

    }

    @Override
    public void getBDLocation(BDLocation bdLocation) {
        lastLocation = bdLocation;
        locationHelper.stop();
    }
}

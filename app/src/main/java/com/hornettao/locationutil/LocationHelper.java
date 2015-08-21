package com.hornettao.locationutil;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by hornettao on 14-10-14.
 */
public class LocationHelper {
    private LocationClient locationClient;
    //自己定义的接口回调，返回定位数据
    private LocationListener locationListener;
    private MyBDLocationListener myBDLocationListener;
    private LocationClientOption locationClientOption;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    //初始化定位组件并启动
    public void start() {

        locationClient = new LocationClient(context);
        myBDLocationListener = new MyBDLocationListener();
        locationClient.registerLocationListener(myBDLocationListener);

        //定位选项
        locationClientOption = new LocationClientOption();
        //方向
        locationClientOption.setNeedDeviceDirect(true);
        //具体地址
        locationClientOption.setIsNeedAddress(true);
        //两次定位请求间隔时间
        locationClientOption.setScanSpan(5000);
        //打开GPS
        locationClientOption.setOpenGps(true);
        //坐标类型
        locationClientOption.setCoorType("bd09ll");
        //定位精确性
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        locationClient.setLocOption(locationClientOption);
        locationClient.start();
    }


    //停止定位
    public void stop() {
        if (locationClient != null) {
            locationClient.stop();
        }
    }

    public class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            locationListener.getBDLocation(bdLocation);
        }
    }

}

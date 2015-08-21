package com.hornettao.MyClass;

import com.baidu.mapapi.search.core.PoiInfo;

import java.io.Serializable;

/**
 * Created by hornettao on 14-10-17.
 */
public class MyPoiInfo implements Serializable {
    public String address;
    public String city;
    public boolean hasCaterDetails;
    public boolean isPano;
    public double latitude;
    public double longtitude;
    public String name;
    public String phoneNum;
    public String postCode;
    public String uid;

    public MyPoiInfo(PoiInfo poiInfo) {
        this.address = poiInfo.address;
        this.city = poiInfo.city;
        this.hasCaterDetails = poiInfo.hasCaterDetails;
        this.isPano = poiInfo.isPano;
        this.latitude = poiInfo.location.latitude;
        this.longtitude = poiInfo.location.longitude;
        this.name = poiInfo.name;
        this.phoneNum = poiInfo.phoneNum;
        this.postCode = poiInfo.postCode;
        this.uid = poiInfo.uid;
    }
}

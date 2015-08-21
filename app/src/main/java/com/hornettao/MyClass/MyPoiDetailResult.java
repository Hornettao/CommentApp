package com.hornettao.MyClass;

import com.baidu.mapapi.search.poi.PoiDetailResult;

import java.io.Serializable;

/**
 * Created by hornettao on 14/10/19.
 */
public class MyPoiDetailResult implements Serializable {
    private String name;
    private double overallRating;
    private double price;
    private String address;

    public MyPoiDetailResult(PoiDetailResult poiDetailResult) {
        this.name = poiDetailResult.getName();
        this.overallRating = poiDetailResult.getOverallRating();
        this.price = poiDetailResult.getPrice();
        this.address = poiDetailResult.getAddress();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

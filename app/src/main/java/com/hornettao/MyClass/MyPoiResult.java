package com.hornettao.MyClass;

import com.baidu.mapapi.search.poi.PoiResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hornettao on 14-10-17.
 */
public class MyPoiResult implements Serializable {
    private List<MyPoiInfo> allPoi;
    private int currentPageCapacity;
    private int currentPageNum;
    private int totalPageNum;
    private int totalPoiNum;

    public MyPoiResult(PoiResult poiResult) {
        this.currentPageCapacity = poiResult.getCurrentPageCapacity();
        this.currentPageNum = poiResult.getCurrentPageNum();
        this.totalPageNum = poiResult.getTotalPageNum();
        this.totalPoiNum = poiResult.getTotalPoiNum();
    }

    public List<MyPoiInfo> getAllPoi() {
        return allPoi;
    }

    public void setAllPoi(List<MyPoiInfo> allPoi) {
        this.allPoi = allPoi;
    }

    public int getCurrentPageCapacity() {
        return currentPageCapacity;
    }

    public void setCurrentPageCapacity(int currentPageCapacity) {
        this.currentPageCapacity = currentPageCapacity;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getTotalPoiNum() {
        return totalPoiNum;
    }

    public void setTotalPoiNum(int totalPoiNum) {
        this.totalPoiNum = totalPoiNum;
    }
}

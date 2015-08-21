package com.hornettao.searchutil;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.hornettao.commentapp.CommentAppApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hornettao on 14/10/20.
 */
public class SearchHelper implements OnGetPoiSearchResultListener{
    private PoiSearch poiSearch;
    private String keyword;
    private int index;
    private BDLocation bdLocation = CommentAppApplication.lastLocation;
    private PoiResult mPoiResult;
    private int totalSearchCount;
    private int nowSearchCount = 0;
    private List<PoiDetailResult> poiDetailResultList = new ArrayList<PoiDetailResult>();
    private SearchListener searchListener;

    public SearchHelper(String keyword, int index, SearchListener searchListener) {
        this.keyword = keyword;
        this.index = index;
        this.searchListener = searchListener;

    }

    public void startSearch() {
        if (keyword != null) {
            poiSearch = PoiSearch.newInstance();
            poiSearch.setOnGetPoiSearchResultListener(this);
            poiSearch.searchInCity(new PoiCitySearchOption()
                    .city(bdLocation.getCity())
                    .keyword(keyword)
                    .pageNum(index));

        }
    }

    public void stopSearch() {
        if (poiSearch != null) {
            poiSearch.destroy();
        }
    }

    //实现poi搜索接口, SearchResult.ERRORNO.AMBIGUOUS_KEYWORD以后修改处理方式
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND
                || poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            //接口返回搜索结果
            searchListener.getPoiDetailResultList(poiDetailResultList);
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            mPoiResult = poiResult;
            totalSearchCount = poiResult.getAllPoi().size();
            for (PoiInfo poiInfo: poiResult.getAllPoi()) {
                String uid = poiInfo.uid;
                if (uid != null) {
                    poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(uid));
                }
            }
            return;
        }
    }
    //实现poi详细搜索接口
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        nowSearchCount++;
        if (poiDetailResult == null || poiDetailResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            System.out.println("--->没有详细信息");
        } else if (poiDetailResult.error == SearchResult.ERRORNO.NO_ERROR) {
            poiDetailResultList.add(poiDetailResult);
        }
        if (nowSearchCount < totalSearchCount) {
            String uid = mPoiResult.getAllPoi().get(nowSearchCount).uid;
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(uid));
        } else {
            //接口返回搜索结果
            searchListener.getPoiDetailResultList(poiDetailResultList);
        }

    }
}

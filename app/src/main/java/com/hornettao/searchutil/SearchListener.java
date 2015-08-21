package com.hornettao.searchutil;

import com.baidu.mapapi.search.poi.PoiDetailResult;

import java.util.List;

/**
 * Created by hornettao on 14/10/20.
 */
public interface SearchListener {
    public void getPoiDetailResultList(List<PoiDetailResult> poiDetailResultList);
}

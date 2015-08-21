package com.hornettao.commentapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hornettao.MyClass.MyPoiDetailResult;
import com.hornettao.adapter.SearchResultAdapter;

import java.util.List;

/**
 * Created by hornettao on 14/10/19.
 */
public class SearchResultFragment extends Fragment {
    //UI相关
    private ListView searchResultListView;
    private SearchResultAdapter searchResultAdapter;
    //搜索相关
    private List<MyPoiDetailResult> myPoiDetailResultList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递的搜索结果
        myPoiDetailResultList = (List<MyPoiDetailResult>) getArguments().getSerializable("searchResult");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, null);
        //初始化UI
        init(view);
        return view;
    }

    //初始化UI
    public void init(View view) {
        //组件实例化
        searchResultListView = (ListView) view.findViewById(R.id.searchResultListView);
        searchResultAdapter = new SearchResultAdapter(getActivity(), myPoiDetailResultList);
        searchResultListView.setAdapter(searchResultAdapter);
    }
}

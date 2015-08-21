package com.hornettao.commentapp;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hornettao on 14-10-8.
 */
public class MainPageFoodFragment extends Fragment {
    private ListView foodListView;
    private Adapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page_food, null);
        foodListView = (ListView) view.findViewById(R.id.foodListView);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getData());
        foodListView.setAdapter((ListAdapter)adapter);
        return view;
    }

    public List<String> getData() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            list.add("数据" + i);
        }
        return list;
    }
}

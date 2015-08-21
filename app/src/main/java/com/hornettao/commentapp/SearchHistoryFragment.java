package com.hornettao.commentapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hornettao on 14-10-17.
 */
public class SearchHistoryFragment extends Fragment {
    //UI相关
    private ListView historyListView;
    private ArrayAdapter<String> historyAdapter;
    private LayoutInflater layoutInflater;
    //sharedPreferences管理
    private SharedPreferences sharedPreferences;
    private List<String> historyList = new ArrayList<String>();
    //fragment相关
    private FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        //初始化layoutInflator
        layoutInflater = LayoutInflater.from(getActivity());
        //读取SharedPreferences中的搜索历史
        sharedPreferences = getActivity().getSharedPreferences("searchHistory", Context.MODE_PRIVATE);
        int size = sharedPreferences.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            historyList.add(sharedPreferences.getString("No." + i, null));
        }
        //初始化Adapter
        historyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        if (size == 0) {
            historyAdapter.add("无搜索历史");
        } else {
            historyAdapter.addAll(historyList);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, null);
        init(view);
        return view;
    }

    //初始化UI
    public void init(View view) {
        //组件实例化
        historyListView = (ListView) view.findViewById(R.id.historyListView);

        //组件详细设置
        //添加listview头部
        View headerView = layoutInflater.inflate(R.layout.listview_header, null);
        TextView headerTextView = (TextView) headerView.findViewById(R.id.listViewHeaderTextView);
        historyListView.addHeaderView(headerView, null, false);
        //添加listview尾部
        View footerView = layoutInflater.inflate(R.layout.listview_footer, null);
        Button footerButton = (Button) footerView.findViewById(R.id.listViewFooterButton);
        //点击按钮---清空搜索历史
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                historyAdapter.clear();
                historyAdapter.add("无搜索历史");
                historyAdapter.notifyDataSetChanged();
            }
        });
        historyListView.addFooterView(footerView, null, false);
        historyListView.setAdapter(historyAdapter);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyword = historyList.get(position - 1);
                AutoCompleteTextView searchAutoCompleteTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.searchAutoCompleteTextView);
                searchAutoCompleteTextView.setText(keyword);
                WaitFragment waitFragment = new WaitFragment();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                waitFragment.setArguments(bundle);

                System.out.println("--->历史纪录到这里啦");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bottomFrameLayout, waitFragment, "waitFragment");
                fragmentTransaction.commit();
            }
        });
    }
}

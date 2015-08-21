package com.hornettao.commentapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.hornettao.MyClass.MyPoiDetailResult;
import com.hornettao.searchutil.SearchHelper;
import com.hornettao.searchutil.SearchListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hornettao on 14/10/18.
 */
public class WaitFragment extends Fragment implements SearchListener{
    //fragment管理
    FragmentManager fragmentManager;
    //搜索相关
    private SearchHelper searchHelper;
    private String keyword;
    private int load_index = 0;
    private List<PoiDetailResult> poiDetailResultList = new ArrayList<PoiDetailResult>();
    private List<MyPoiDetailResult> myPoiDetailResultList = new ArrayList<MyPoiDetailResult>();
    //UI相关
    private ImageView imageView;
    //动画相关
    private final int TIME = 300;
    private int count = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME);
            count++;
            switch (count % 3) {
                case 0:
                    imageView.setImageResource(R.drawable.wait_0);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.wait_1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.wait_2);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递的keyword
        keyword = getArguments().getString("keyword");
        System.out.println("--->关键词" + keyword);
        //初始化manager
        fragmentManager = getFragmentManager();
        //初始化搜索
        searchHelper = new SearchHelper(keyword, load_index, this);
        searchHelper.startSearch();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait, null);
        //初始化UI
        init(view);
        return view;
    }

    //初始化UI
    public void init(View view) {
        imageView = (ImageView) view.findViewById(R.id.waitImageView);
        handler.postDelayed(runnable, TIME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchHelper.stopSearch();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //刷新Fragment
    public void refreshFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottomFrameLayout, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    public void getPoiDetailResultList(List<PoiDetailResult> poiDetailResultList) {
        this.poiDetailResultList = poiDetailResultList;
        if (this.poiDetailResultList.size() <= 0) {
            //没有搜索结果
            System.out.println("--->无搜索结果");
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("无结果")
                    .setMessage("请重新搜索")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshFragment(new SearchHistoryFragment(), "searchHistoryFragment");
                        }
                    }).create();
            alertDialog.show();
        } else {
            //有搜索结果
            System.out.println("--->" + poiDetailResultList);
            for (PoiDetailResult poiDetailResult: poiDetailResultList) {
                MyPoiDetailResult myPoiDetailResult = new MyPoiDetailResult(poiDetailResult);
                myPoiDetailResultList.add(myPoiDetailResult);
            }
            //传递结果
            SearchResultFragment searchResultFragment = new SearchResultFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("searchResult", (Serializable) myPoiDetailResultList);
            searchResultFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.bottomFrameLayout, searchResultFragment, "searchResultFragment");
            fragmentTransaction.commit();
        }
    }
}

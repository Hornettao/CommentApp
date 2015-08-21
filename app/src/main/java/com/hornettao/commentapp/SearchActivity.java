package com.hornettao.commentapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hornettao on 14-10-17.
 */
public class SearchActivity extends Activity implements
        OnGetSuggestionResultListener{
    //不变量
    private final int HISTORY_NUM = 10;
    //Fragment管理
    private FragmentManager fragmentManager;
    //获取定位信息，此处应该增加是否为空判断，以后做!!!!!!
    private BDLocation bdLocation = CommentAppApplication.lastLocation;
    //UI相关
    private Button backButton;
    private AutoCompleteTextView searchAutoCompleteTextView;
    private Button searchButton;
    private boolean isFirstEntered = false;
    //搜索相关
    private SuggestionSearch suggestionSearch;
    private ArrayAdapter<String> suggestionAdapter;
    private List<String> historyList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //初始化搜索
        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(this);
        suggestionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        init();
    }
    //初始化UI
    public void init() {
        //默认添加SearchHistoryFragment
        SearchHistoryFragment searchHistoryFragment = new SearchHistoryFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction transition = fragmentManager.beginTransaction();
        transition.add(R.id.bottomFrameLayout, searchHistoryFragment, "searchHistoryFragment");
        transition.commit();


        //组件实例化
        backButton = (Button) this.findViewById(R.id.backButton);
        searchAutoCompleteTextView = (AutoCompleteTextView) this.findViewById(R.id.searchAutoCompleteTextView);
        searchButton = (Button) this.findViewById(R.id.searchButton);

        //组件添加监听器
        backButton.setOnClickListener(new BackButtonClickListener());
        searchAutoCompleteTextView.addTextChangedListener(new MyTextChangedListener());
        searchAutoCompleteTextView.setOnEditorActionListener(new MyEditorActionListener());
        searchAutoCompleteTextView.setOnFocusChangeListener(new MyFocusChangeListener());
        searchButton.setOnClickListener(new SearchButtonClickLiser());

        //组件详细设置
        searchAutoCompleteTextView.setAdapter(suggestionAdapter);
    }
    //监听搜索栏焦点变化
    //第一次进入时，获得焦点不进行操作
    //之后，获得焦点，则更新fragment为搜索历史
    class MyFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                if (isFirstEntered) {
                    isFirstEntered = !isFirstEntered;
                    return;
                }
                refreshFragment(new SearchHistoryFragment(), "searchHistoryFragment");
            }
        }
    }
    //点击搜索按钮---进行poi搜索
    class SearchButtonClickLiser implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取关键字
            String keyword = searchAutoCompleteTextView.getText().toString();
            //更新Fragment为WaitFragment---等待数据时的动画, 在这里处理搜索
            WaitFragment waitFragment = new WaitFragment();
            Bundle bundle = new Bundle();
            waitFragment.setArguments(bundle);
            bundle.putString("keyword", keyword);
            System.out.println("--->到这里啦");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.bottomFrameLayout, waitFragment, "waitFragment");
            fragmentTransaction.commit();

            //更新SharedPreferences
            updateSharedPreferences(keyword);

            //收起软键盘
            InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            //清除焦点
            searchAutoCompleteTextView.clearFocus();
        }
    }
    //监听搜索栏的回车按键，按下回车进行poi搜索
    class MyEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            //这里的actionId添加一个EditorInfo.IME_ACTION_UNSPECIFIED, 因为存在suggestionadapter
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                //获取关键字
                String keyword = searchAutoCompleteTextView.getText().toString();
                //更新Fragment为WaitFragment---等待数据时的动画, 在这里处理搜索
                WaitFragment waitFragment = new WaitFragment();
                Bundle bundle = new Bundle();
                waitFragment.setArguments(bundle);
                bundle.putString("keyword", keyword);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bottomFrameLayout, waitFragment, "waitFragment");
                fragmentTransaction.commit();


                //更新SharedPreferences
                updateSharedPreferences(keyword);

                //收起软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                //清除焦点
                searchAutoCompleteTextView.clearFocus();
            }
            return false;
        }
    }

    //实现建议搜索接口
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        suggestionAdapter.clear();
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            suggestionAdapter.add("无搜索建议");
            return;
        }
        for (SuggestionResult.SuggestionInfo suggestionInfo: suggestionResult.getAllSuggestions()) {
            if (suggestionInfo.key != null) {
                suggestionAdapter.add(suggestionInfo.key);
            }
        }
        suggestionAdapter.notifyDataSetChanged();
    }

    //点击返回按键---返回
    class BackButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    //监听搜索栏内容，发起建议搜索
    class MyTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() <= 0) {
                return;
            }
            String keyword = searchAutoCompleteTextView.getText().toString();
            suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(bdLocation.getCity()).keyword(keyword));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    //更新SharedPreferences
    public void updateSharedPreferences(String newSearch) {
        //读取SharedPreferences中的搜索历史
        SharedPreferences sharedPreferences = this.getSharedPreferences("searchHistory", Context.MODE_PRIVATE);
        historyList.clear();
        int size = sharedPreferences.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            historyList.add(sharedPreferences.getString("No." + i, null));
        }
//        //如果曾经搜索过，删除原来的搜索记录---此算法以后考虑加入与否
//        if (historyList.contains(newSearch)) {
//            historyList.remove(newSearch);
//        }
        //更新SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        historyList.add(0, newSearch);
        editor.clear();
        int totalNum = historyList.size() > HISTORY_NUM ? HISTORY_NUM : historyList.size();
        editor.putInt("size", totalNum);
        for (int i = 0; i < totalNum; i++) {
            editor.putString("No." + i, historyList.get(i));
        }
        editor.commit();
    }

    //刷新Fragment
    public void refreshFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bottomFrameLayout, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        suggestionSearch.destroy();
    }
}

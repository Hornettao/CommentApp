package com.hornettao.commentapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hornettao.fragmenttabhost.FragmentTabHost;


public class MainActivity extends Activity {
    private FragmentTabHost fragmentTabHost;
    private FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;

    private Button locationButton;
    private EditText searchEditText;
    //判断searchEditText是否获得了焦点
    private boolean isFocused = false;
    //tabhost控制的fragment页面
    private Class fragmentArray[] = {MainPageFragment.class, SurroundingFragment.class, HiFragment.class, CollectFragment.class,MyFragment.class};

    //tabhost上的图片
    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_square_btn, R.drawable.tab_selfinfo_btn, R.drawable.tab_more_btn};

    //tabhost上的文字
    private String mTextviewArray[] = {"首页", "周边", "嗨", "收藏", "我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        initView();
    }

    //初始化界面
    private void initView(){
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化组件
        locationButton = (Button) this.findViewById(R.id.locationButton);
        searchEditText = (EditText) this.findViewById(R.id.searchEditText);

        //searchEditText用户点击则跳转到搜索界面, onStop中取消searchEditText焦点
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isFocused = true;
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        //按钮监听器
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //实例化TabHost对象，得到TabHost
        fragmentTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, fragmentManager, R.id.centerLinearLayout);

        //得到fragment页面的个数
        int count = fragmentArray.length;

        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字(见下面的 getTabItemView(int index) )
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中，并与fragment进行绑定
            fragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    //给tabhost设置图片和文字
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //如果searchEditText获取了焦点, 取消焦点
        if (isFocused) {
            //为了使clearFocus()生效，需要在edittext的父组件内加入
            // android:focusable="true"
            //android:focusableInTouchMode="true"
            searchEditText.clearFocus();
        }
    }
}

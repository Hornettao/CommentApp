package com.hornettao.commentapp;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hornettao.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hornettao on 14-10-7.
 */
public class MainPageActivityFragment extends Fragment {
    private ViewPager viewPager;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    //viewpager图片
    private List<Drawable> drawableList;

    //indicator小圆点图片
    private List<ImageView> indicatorImageViewList;
    private ViewPagerAdapter viewPagerAdapter;

    //自动循环间隔时间
    private final int TIME = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page_activity, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        imageView1 = (ImageView) view.findViewById(R.id.imageview1);
        imageView2 = (ImageView) view.findViewById(R.id.imageview2);
        imageView3 = (ImageView) view.findViewById(R.id.imageview3);
        imageView4 = (ImageView) view.findViewById(R.id.imageview4);

        //viewPager中显示的图片
        drawableList = new ArrayList<Drawable>();
        drawableList.add(getResources().getDrawable(R.drawable.germany));
        drawableList.add(getResources().getDrawable(R.drawable.america));
        drawableList.add(getResources().getDrawable(R.drawable.lithuania));
        drawableList.add(getResources().getDrawable(R.drawable.france));

        //indicator小圆点图片
        indicatorImageViewList = new ArrayList<ImageView>();
        indicatorImageViewList.add(imageView1);
        indicatorImageViewList.add(imageView2);
        indicatorImageViewList.add(imageView3);
        indicatorImageViewList.add(imageView4);

        //ViewPagerAdapter初始化
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), viewPager, drawableList, indicatorImageViewList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(viewPagerAdapter);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //手按下, 停止自动循环
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacks(runnable);
                        break;
                    //手抬起, 开始自动循环
                    case MotionEvent.ACTION_UP:
                        handler.postDelayed(runnable, TIME);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        viewPagerAdapter.notifyDataSetChanged();

        //自动循环
        if (drawableList.size() > 1) {
            viewPager.setCurrentItem(1);
            handler.postDelayed(runnable, TIME);
        }
        return view;
    }

    //初始化Handler和Runnable
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this, TIME);
                int count = viewPagerAdapter.getCount();

                if (count > 3) { //大于1个则大于3个
                    int index = viewPager.getCurrentItem();
                    index = index % (count - 2) + 1;
                    viewPager.setCurrentItem(index, true);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };
}

package com.hornettao.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hornettao.commentapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hornettao on 14-10-8.
 */
public class ViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;

    //真实列表
    private List<Drawable> mDrawableList;
    private List<ImageView> mIndicatorImageViewList;

    //显示列表，比真实列表多两个元素 ----- 显示列表 = 真实列表尾 + 真实列表 + 真实列表头
    private List<View> showViewList;
    private LayoutInflater layoutInflater;
    private int mDrawableListSize;

    //自定义构造器
    public ViewPagerAdapter(Context context, ViewPager viewPager, List<Drawable> drawableList, List<ImageView> indicatorImageViewList) {
        mContext = context;
        mViewPager = viewPager;
        mDrawableList = drawableList;
        mIndicatorImageViewList = indicatorImageViewList;

        layoutInflater = LayoutInflater.from(context);
        mDrawableListSize = mDrawableList.size();


        if (mDrawableList != null) {
            ////将真实列表的N-1位置 传入 显示列表的0位置
            showViewList = new ArrayList<View>();
            View view = layoutInflater.inflate(R.layout.view_pager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

            imageView.setImageDrawable(mDrawableList.get(mDrawableListSize - 1));
            showViewList.add(view);

            //viewPager中view的个数大于1个时，进行循环滑动
            if (mDrawableListSize > 1) {
                //将真实列表的0~N-1位置 传入 显示列表的1~N-2位置
                for (Drawable drawable: mDrawableList) {
                    view = layoutInflater.inflate(R.layout.view_pager_item, null);
                    imageView = (ImageView) view.findViewById(R.id.imageView);
                    imageView.setImageDrawable(drawable);
                    showViewList.add(view);
                }

                //将真是列表的0位置 传入 显示列表的N-1位置
                view = layoutInflater.inflate(R.layout.view_pager_item, null);
                imageView = (ImageView) view.findViewById(R.id.imageView);
                imageView.setImageDrawable(mDrawableList.get(0));
                showViewList.add(view);
            }
        }
    }
    @Override
    public int getCount() {
        return showViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }


    //这里可以加点击事件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = showViewList.get(position);

        //举例
        //imageview =(ImageView) view.findViewById()
        //imageView.setonClickListener

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(showViewList.get(position));
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    //i代表position
    @Override
    public void onPageSelected(int position) {
        //viewPager中view的个数大于1，进行循环滑动
        if (mDrawableListSize > 1) {
            if (position == 0 ){
                position = showViewList.size() - 2;
                mViewPager.setCurrentItem(position, false);
            } else if (position == showViewList.size() - 1) {
                position = 1;
                mViewPager.setCurrentItem(position, false);
            }
        }

        for (int i = 0; i < mIndicatorImageViewList.size(); i++) {
            if (i == (position - 1)) {
                mIndicatorImageViewList.get(i).setImageResource(R.drawable.yellow);
            } else {
                mIndicatorImageViewList.get(i).setImageResource(R.drawable.blue);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

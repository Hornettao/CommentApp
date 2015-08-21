package com.hornettao.commentapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hornettao on 14-10-7.
 */
public class MainPageFragment extends Fragment {
    private Button activityButton;
    private Button foodButton;
    private Button entertainButton;
    private Button travelButton;
    private Button hotelButton;
    private Button discountButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //首页的4个fragment
    private MainPageActivityFragment mainPageActivityFragment;
    private MainPageFoodFragment mainPageFoodFragment;
    private MainPageEntertainFragment mainPageEntertainFragment;
    private MainPageTravelFragment mainPageTravelFragment;
    private MainPageHotelFragment mainPageHotelFragment;
    private MainPageDiscountFragment mainPageDiscountFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, null);
        activityButton = (Button) view.findViewById(R.id.activityButton);
        foodButton = (Button) view.findViewById(R.id.foodButton);
        entertainButton = (Button) view.findViewById(R.id.entertainButton);
        travelButton = (Button) view.findViewById(R.id.travelButton);
        hotelButton = (Button) view.findViewById(R.id.hotelButton);
        discountButton = (Button) view.findViewById(R.id.discountButton);

        //首页右侧Fragment，默认为 活动
        fragmentTransaction = fragmentManager.beginTransaction();
        mainPageActivityFragment = new MainPageActivityFragment();
        fragmentTransaction.add(R.id.rightLinearLayout, mainPageActivityFragment, "mainPageActivityFragment");
        fragmentTransaction.commit();

        activityButton.setOnClickListener(new MyOnClickListener(0));
        foodButton.setOnClickListener(new MyOnClickListener(1));
        entertainButton.setOnClickListener(new MyOnClickListener(2));
        travelButton.setOnClickListener(new MyOnClickListener(3));
        hotelButton.setOnClickListener(new MyOnClickListener(4));
        discountButton.setOnClickListener(new MyOnClickListener(5));
        return view;
    }

    //左侧6个按钮的监听器
    //0 --- activityButton
    //1 --- foodButton
    //2 --- entertainButton
    //3 --- travelButton
    //4 --- hotelButton
    //5 --- discountButton
    public class MyOnClickListener implements View.OnClickListener{
        private int mPosition;
        public MyOnClickListener(int position) {
            mPosition = position;
        }
        @Override
        public void onClick(View v) {
            switch (mPosition) {
                case 0:
                    //改变左侧按钮状态
                    buttonStateClear();
                    activityButton.setBackgroundResource(R.drawable.yellow);
                    //改变右侧fragment
                    changeRightFragment(0);
                    break;
                case 1:
                    //改变左侧按钮状态
                    buttonStateClear();
                    foodButton.setBackgroundResource(R.drawable.yellow);
                    //改变右侧fragment
                    changeRightFragment(1);
                    break;
                case 2:
                    //改变左侧按钮状态
                    buttonStateClear();
                    entertainButton.setBackgroundResource(R.drawable.yellow);
                    //改变右侧fragment
                    changeRightFragment(2);
                    break;
                case 3:
                    //改变左侧按钮状态
                    buttonStateClear();
                    travelButton.setBackgroundResource(R.drawable.yellow);
                    //改变右侧fragment
                    changeRightFragment(3);
                    break;
                case 4:
                    //改变左侧按钮状态
                    buttonStateClear();
                    hotelButton.setBackgroundResource(R.drawable.yellow);
                    //改变右侧fragment
                    changeRightFragment(4);
                    break;
                case 5:
                    //改变左侧按钮状态
                    buttonStateClear();
                    discountButton.setBackgroundResource(R.drawable.yellow);
                    //改变右侧fragment
                    changeRightFragment(5);
                    break;
                default:
                    break;
            }
        }
    }

    //显示首页左侧按钮点击状态
    public void buttonStateClear() {
        activityButton.setBackgroundResource(R.drawable.blue);
        foodButton.setBackgroundResource(R.drawable.blue);
        entertainButton.setBackgroundResource(R.drawable.blue);
        travelButton.setBackgroundResource(R.drawable.blue);
        hotelButton.setBackgroundResource(R.drawable.blue);
        discountButton.setBackgroundResource(R.drawable.blue);
    }

    //改变右部6个fragment
    //0 --- mainPageActivityFragment
    //1 --- mainPageFoodFragment
    //2 --- mainPageEntertainFragment
    //3 --- mainPageTravelFragment
    //4 --- mainPageHotelFragment
    //5 --- mainPageDiscountFragment
    public void changeRightFragment(int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //判断fragment是否为空，如果不为空，将其隐藏；如果为空，不做任何操作
        if (mainPageActivityFragment != null) {
            fragmentTransaction.hide(mainPageActivityFragment);
        }
        if (mainPageFoodFragment != null) {
            fragmentTransaction.hide(mainPageFoodFragment);
        }
        if (mainPageEntertainFragment != null) {
            fragmentTransaction.hide(mainPageEntertainFragment);
        }
        if (mainPageTravelFragment != null) {
            fragmentTransaction.hide(mainPageTravelFragment);
        }
        if (mainPageHotelFragment != null) {
            fragmentTransaction.hide(mainPageHotelFragment);
        }
        if (mainPageDiscountFragment != null) {
            fragmentTransaction.hide(mainPageDiscountFragment);
        }

        //显示点击的按钮所对应的fragment
        switch (position) {
            case 0:
                fragmentTransaction.show(mainPageActivityFragment);
                break;
            case 1:
                if (mainPageFoodFragment == null) {
                    mainPageFoodFragment = new MainPageFoodFragment();
                    fragmentTransaction.add(R.id.rightLinearLayout, mainPageFoodFragment, "mainPageFoodFragment");
                } else {
                    fragmentTransaction.show(mainPageFoodFragment);
                }
                break;
            case 2:
                if (mainPageEntertainFragment == null) {
                    mainPageEntertainFragment = new MainPageEntertainFragment();
                    fragmentTransaction.add(R.id.rightLinearLayout, mainPageEntertainFragment, "mainPageEntertainFragment");
                } else {
                    fragmentTransaction.show(mainPageEntertainFragment);
                }
                break;
            case 3:
                if (mainPageTravelFragment == null) {
                    mainPageTravelFragment = new MainPageTravelFragment();
                    fragmentTransaction.add(R.id.rightLinearLayout, mainPageTravelFragment, "mainPageTravelFragment");
                } else {
                    fragmentTransaction.show(mainPageTravelFragment);
                }
                break;
            case 4:
                if (mainPageHotelFragment == null) {
                    mainPageHotelFragment = new MainPageHotelFragment();
                    fragmentTransaction.add(R.id.rightLinearLayout, mainPageHotelFragment, "mainPageHotelFragment");
                } else {
                    fragmentTransaction.show(mainPageHotelFragment);
                }
                break;
            case 5:
                if (mainPageDiscountFragment == null) {
                    mainPageDiscountFragment = new MainPageDiscountFragment();
                    fragmentTransaction.add(R.id.rightLinearLayout, mainPageDiscountFragment, "mainPageDiscountFragment");
                } else {
                    fragmentTransaction.show(mainPageDiscountFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }
}

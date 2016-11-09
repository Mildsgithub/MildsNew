package com.xm.pc.mildsnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import Fragm.MainFragment;
import utils.CommonUtils;
import utils.TitileUtils;
import views.LazyViewPager;
import views.SlidingMenuUtils;
import views.ViewPagerIndicator;

public class MainActivity extends AppCompatActivity {


    private LazyViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingMenuUtils slidingMenuUtils = new SlidingMenuUtils();
        slidingMenuUtils.initSlidingMenu(MainActivity.this);

        String newsTitle = CommonUtils.getSp("newsTitle");

        String[] split = newsTitle.split(",");
        for (int i = 0; i < 20; i++) {
            TitileUtils.myChannel.add(split[i]);
        }
        for (int i=20;i<split.length;i++){
            TitileUtils.moreChannel.add(split[i]);
        }


        viewPager = (LazyViewPager) findViewById(R.id.viewPager);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.viewPagerIndicator);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MainFragment.getInstance( TitileUtils.myChannel.get(position).substring(
                        TitileUtils.myChannel.get(position).indexOf(":") + 1));
            }

            @Override
            public int getCount() {
                return  TitileUtils.myChannel.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return  TitileUtils.myChannel.get(position).substring(0,
                        TitileUtils.myChannel.get(position).indexOf(":"));
            }
        });
        viewPagerIndicator.setViewPager(viewPager);
    }
}

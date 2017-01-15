package com.weiteng.weitengapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.Application;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.ui.adapter.MainIconPagerAdapter;
import com.weiteng.weitengapp.ui.factory.MainViewPagerFragmentFactory;
import com.weiteng.weitengapp.ui.view.Fragment;
import com.weiteng.weitengapp.ui.view.IconViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseCompatActivity {
    private ViewPager mViewPager;
    private IconViewPager mIconPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.activity_main_vp);
        mIconPager = (IconViewPager) findViewById(R.id.activity_main_ip);
    }

    @Override
    protected void initData() {
        MainIconPagerAdapter adapter = new MainIconPagerAdapter(initFragments(), getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIconPager.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(MainViewPagerFragmentFactory.create((Application) mApp, 0));
        //fragments.add(MainViewPagerFragmentFactory.create((Application) mApp, 1));
        fragments.add(MainViewPagerFragmentFactory.create((Application) mApp, 2));
        fragments.add(MainViewPagerFragmentFactory.create((Application) mApp, 3));

        return fragments;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}

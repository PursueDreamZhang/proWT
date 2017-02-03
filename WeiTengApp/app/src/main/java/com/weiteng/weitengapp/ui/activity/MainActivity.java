package com.weiteng.weitengapp.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.Application;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.EventMessage;
import com.weiteng.weitengapp.interf.IEventCode;
import com.weiteng.weitengapp.ui.adapter.MainIconPagerAdapter;
import com.weiteng.weitengapp.ui.factory.MainViewPagerFragmentFactory;
import com.weiteng.weitengapp.ui.view.Fragment;
import com.weiteng.weitengapp.ui.view.IconViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.aty_main_vp);
        mIconPager = (IconViewPager) findViewById(R.id.aty_main_ip);
    }

    @Override
    protected void initData() {
        MainIconPagerAdapter adapter = new MainIconPagerAdapter(initFragments(), getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIconPager.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);

        EventBus.getDefault().register(this);
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

    @Subscribe
    public void onEventMainThread(EventMessage event) {
        switch (event.getCode()) {
            case IEventCode.EVENT_USER_LOGOUT:
                finish();
                break;
            case IEventCode.EVENT_QUERY_RECORD:
                mViewPager.setCurrentItem(1);
        }
    }
}

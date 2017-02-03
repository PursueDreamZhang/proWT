package com.weiteng.weitengapp.ui.adapter;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weiteng.weitengapp.base.BaseFragment;
import com.weiteng.weitengapp.ui.view.Fragment;
import com.weiteng.weitengapp.ui.view.IconViewPager;

public class MainIconPagerAdapter extends FragmentPagerAdapter implements IconViewPager.IconPagerAdapter {
	private List<Fragment> mFragments;

	public MainIconPagerAdapter(List<Fragment> fragments, FragmentManager fm) {
		super(fm);
		mFragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getIconResId(int position) {
		return mFragments.get(position).getIcon();
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public String getPageTitle(int position) {
		return mFragments.get(position).getTitle();
	}
}

package com.weiteng.weitengapp.ui.factory;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.Application;
import com.weiteng.weitengapp.ui.fragment.HomeFragment;
import com.weiteng.weitengapp.ui.fragment.PersonalFragment;
import com.weiteng.weitengapp.ui.fragment.RecordFragment;
import com.weiteng.weitengapp.ui.fragment.ShopFragment;
import com.weiteng.weitengapp.ui.view.Fragment;
import com.weiteng.weitengapp.util.ResourceUtils;

public class MainViewPagerFragmentFactory {
	public static Fragment create(Application app, int position) {
		String[] tabs = ResourceUtils.getStringArray(R.array.main_tab_names);
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			fragment.setIcon(R.drawable.main_tab_home_selector);
			break;
		case 1:
			fragment = new ShopFragment();
			fragment.setIcon(R.drawable.main_tab_shop_selector);
			break;
		case 2:
			fragment = new RecordFragment();
			fragment.setIcon(R.drawable.main_tab_record_selector);
			break;
		case 3:
			fragment = new PersonalFragment();
			fragment.setIcon(R.drawable.main_tab_personal_selector);
			break;
		}
		fragment.setTitle(tabs[position]);
		return fragment;
	}
}

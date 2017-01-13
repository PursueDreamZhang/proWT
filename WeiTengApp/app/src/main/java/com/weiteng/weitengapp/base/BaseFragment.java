package com.weiteng.weitengapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected BaseApplication mApp;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mApp = (BaseApplication) getActivity().getApplication();
        initData();
    }
    protected abstract void initData();
}
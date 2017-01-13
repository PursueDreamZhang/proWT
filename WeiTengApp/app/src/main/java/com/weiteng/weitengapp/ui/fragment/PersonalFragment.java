package com.weiteng.weitengapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiteng.weitengapp.ui.view.Fragment;

/**
 * Created by Admin on 2017/1/7.
 */

public class PersonalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("PersonalFragment");
        tv.setTextSize(20);
        return tv;
    }

    @Override
    protected View getSuccessView() {
        return null;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initData() {

    }
}

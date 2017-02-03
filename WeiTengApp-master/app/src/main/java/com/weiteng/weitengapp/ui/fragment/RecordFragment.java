package com.weiteng.weitengapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.ui.view.Fragment;

/**
 * Created by zcf on 2017/1/7.
 */

public class RecordFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getContext(), R.layout.fragment_record, null);
        return root;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void requestData() {
    }
}

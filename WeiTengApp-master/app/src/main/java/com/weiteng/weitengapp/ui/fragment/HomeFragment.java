package com.weiteng.weitengapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.ui.view.Fragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/1/7.
 */

public class HomeFragment extends Fragment {
    @InjectView(R.id.slider_home)
    SliderLayout mSlider;
    @InjectView(R.id.lv_home)
    ListView mLvHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initData() {
        String url1 = "http://weiteng.tdwebsite.cn/statics/static/h-ui.admin/images/b-1.jpg";
        String url2 = "http://weiteng.tdwebsite.cn/statics/static/h-ui.admin/images/b-2.jpg";
        TextSliderView textSliderView1 = new TextSliderView(getContext());
        textSliderView1.image(url1);
        textSliderView1.setScaleType(BaseSliderView.ScaleType.Fit);
        mSlider.addSlider(textSliderView1);

        TextSliderView textSliderView2 = new TextSliderView(getContext());
        textSliderView2.image(url2);
        textSliderView2.setScaleType(BaseSliderView.ScaleType.Fit);

        mSlider.addSlider(textSliderView2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

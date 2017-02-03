package com.weiteng.weitengapp.ui.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.NoticeBean;
import com.weiteng.weitengapp.bean.NoticeDetailBean;
import com.weiteng.weitengapp.ui.activity.NoticeDetailActivity;
import com.weiteng.weitengapp.ui.adapter.NoticeListAdapter;
import com.weiteng.weitengapp.ui.view.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 2017/1/7.
 */

public class HomeFragment extends Fragment {
    private SliderLayout sl_slider;
    private PullToRefreshListView ptrlv_notice_list;
    private NoticeListAdapter noticeListAdapter;

    private List<NoticeBean> notices = null;
    private List<NoticeBean> slides = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        notices = new ArrayList<>();

        View root = View.inflate(getContext(), R.layout.fragment_home, null);
        sl_slider = (SliderLayout) root.findViewById(R.id.fgmt_home_sl_slider);
        ptrlv_notice_list = (PullToRefreshListView) root.findViewById(R.id.fgmt_home_ptrlv_notice_list);

        ptrlv_notice_list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        noticeListAdapter = new NoticeListAdapter(getContext(), notices);
        ptrlv_notice_list.setAdapter(noticeListAdapter);
        ptrlv_notice_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
                ApiManager.getApiManager().getNoticeList("103", "list", notices.size() / 10 + 2, 10, new ApiManager.RequestCallBack<NoticeBean>() {
                    @Override
                    public void onSuccess(NoticeBean data) {
                    }

                    @Override
                    public void onSuccess(List<NoticeBean> data) {
                        notices.addAll(data);
                        refreshView.onRefreshComplete();
                        noticeListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String error) {
                        mApp.showToast("加载失败");
                        refreshView.onRefreshComplete();
                    }
                });
            }
        });

        ptrlv_notice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final SweetAlertDialog progDlg = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                progDlg.setCancelable(false);
                progDlg.setTitleText("正在加载").show();

                int idx = notices.get(i - 1).url.lastIndexOf("/");
                String id = notices.get(i - 1).url.substring(idx + 1);
                ApiManager.getApiManager().getNoticeDetail(id, new ApiManager.RequestCallBack<NoticeDetailBean>() {
                    @Override
                    public void onSuccess(NoticeDetailBean data) {
                    }

                    @Override
                    public void onSuccess(List<NoticeDetailBean> data) {
                        progDlg.dismiss();
                        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
                        intent.putExtra("notice_detail", data.get(0));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error) {
                        progDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        progDlg.setTitleText("加载失败，请重试");
                    }
                });
            }
        });

        return root;
    }

    @Override
    protected void initData() {
        requestData();
    }

    @Override
    protected void requestData() {
        String imgUrl1 = "http://weiteng.tdwebsite.cn/statics/static/h-ui.admin/images/b-1.jpg";
        String imgUrl2 = "http://weiteng.tdwebsite.cn/statics/static/h-ui.admin/images/b-2.jpg";

        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = (int) (width / 3.45);
        ViewGroup.LayoutParams layoutParams = sl_slider.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        sl_slider.setLayoutParams(layoutParams);

        TextSliderView textSliderView1 = new TextSliderView(getContext());
        textSliderView1.image(imgUrl1);
        textSliderView1.setScaleType(BaseSliderView.ScaleType.Fit);
        TextSliderView textSliderView2 = new TextSliderView(getContext());
        textSliderView2.image(imgUrl2);
        textSliderView2.setScaleType(BaseSliderView.ScaleType.Fit);
        sl_slider.addSlider(textSliderView1);
        sl_slider.addSlider(textSliderView2);

        ApiManager.getApiManager().getNoticeList("103", "list", 1, 10, new ApiManager.RequestCallBack<NoticeBean>() {
            @Override
            public void onSuccess(NoticeBean data) {
            }

            @Override
            public void onSuccess(List<NoticeBean> data) {
                notices.addAll(data);
                noticeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                mApp.showToast("加载失败");
            }
        });
    }
}

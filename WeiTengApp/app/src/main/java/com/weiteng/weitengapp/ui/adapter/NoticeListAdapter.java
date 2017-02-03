package com.weiteng.weitengapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseAdapter;
import com.weiteng.weitengapp.bean.NoticeBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2017/1/15.
 */

public class NoticeListAdapter extends BaseAdapter<NoticeBean> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Context mContext;

    public NoticeListAdapter(Context context, List<NoticeBean> list) {
        super(list);
        mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.fgmt_home_noticelist_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) view.findViewById(R.id.fgmt_home_noticelist_item_tv_title);
            holder.tv_time = (TextView) view.findViewById(R.id.fgmt_home_noticelist_item_tv_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_title.setText(list.get(i).title);
        holder.tv_time.setText("发布时间：" + sdf.format(new Date(Long.parseLong(list.get(i).createTime) * 1000)));

        return view;
    }

    class ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
    }
}

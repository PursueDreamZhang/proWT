package com.weiteng.weitengapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.bean.SpendingInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class RecordItemAdapter extends BaseAdapter{

    private ArrayList<SpendingInfo> items = new ArrayList<SpendingInfo>();

    private LayoutInflater mInflater;

    public RecordItemAdapter(ArrayList<SpendingInfo> items, Context context) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SpendingInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.vw_list_item,null);
        TextView textView = (TextView) view.findViewById(R.id.tvSum);

        textView.setText(items.get(position).getCount());


        return view;
    }
}

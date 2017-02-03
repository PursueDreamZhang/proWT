package com.weiteng.weitengapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.bean.ExchangeBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class RecordItemAdapter extends BaseAdapter {

    private ArrayList<ExchangeBean> items = new ArrayList<ExchangeBean>();

    private LayoutInflater mInflater;

    private int mCategory;



    public RecordItemAdapter(ArrayList<ExchangeBean> items, Context context, String category) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
        mCategory = Integer.parseInt(category);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ExchangeBean getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.vw_list_item, null);
            holder.category = (TextView) convertView.findViewById(R.id.tvTitle_record);
            holder.money = (TextView) convertView.findViewById(R.id.tvMoney_record);
            holder.shop = (TextView) convertView.findViewById(R.id.tvShop_record);
            holder.data = (TextView) convertView.findViewById(R.id.tvDateTime_record);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon_record);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ExchangeBean exchangeBean = items.get(position);
        holder.shop.setText(exchangeBean.Sshopname);
        switch (mCategory) {
            case 501:
                holder.category.setText("充值");

                break;
            case 502:
                holder.category.setText("消费");

                break;
            case 503:
                holder.category.setText("提现");

                break;
            case 504:
                holder.category.setText("转存");

                break;

        }
        holder.money.setText("金额："+exchangeBean.EexchangeAmount);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = format.format(Long.parseLong(exchangeBean.EexchangeTime));
        holder.data.setText(time);
        switch (mCategory) {
            case 501:
                holder.icon.setImageResource(R.mipmap.recharge);
                break;
            case 502:
                holder.icon.setImageResource(R.mipmap.spending);

                break;
            case 503:
                holder.icon.setImageResource(R.mipmap.cash);

                break;
            case 504:
                holder.icon.setImageResource(R.mipmap.change);

                break;

        }


        return convertView;
    }

    private class ViewHolder {
        TextView money;
        TextView data;
        TextView category;
        TextView shop;
        ImageView icon;


    }
}

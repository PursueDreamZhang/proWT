package com.weiteng.weitengapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseAdapter;
import com.weiteng.weitengapp.bean.MoneyBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 2017/1/15.
 */

public class MoneyListAdapter extends BaseAdapter<MoneyBean> {
    DecimalFormat df = new DecimalFormat("#0.00");
    private Context mContext;

    public MoneyListAdapter(Context context, List<MoneyBean> list) {
        super(list);
        mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.dlg_preorder_moneylist_item, null);
            holder = new ViewHolder();
            holder.tv_id = (TextView) view.findViewById(R.id.dlg_preorder_moneylist_item_tv_id);
            holder.tv_amount = (TextView) view.findViewById(R.id.dlg_preorder_moneylist_item_tv_amount);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_id.setText("[id:" + list.get(i).id + "]");
        holder.tv_amount.setText("￥" + df.format(list.get(i).money));

        return view;
    }

    class ViewHolder {
        public TextView tv_id;
        public TextView tv_amount;
    }
}

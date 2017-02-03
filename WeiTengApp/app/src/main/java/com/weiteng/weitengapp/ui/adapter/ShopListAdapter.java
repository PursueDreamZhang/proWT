package com.weiteng.weitengapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseAdapter;
import com.weiteng.weitengapp.bean.ShopBean;

import java.util.List;

/**
 * Created by Admin on 2017/1/15.
 */

public class ShopListAdapter extends BaseAdapter<ShopBean> {
    private Context mContext;

    public ShopListAdapter(Context context, List<ShopBean> list) {
        super(list);
        mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.dlg_preorder_shoplist_item, null);
            holder = new ViewHolder();

            holder.tv_shopname = (TextView) view.findViewById(R.id.dlg_preorder_shoplist_item_tv_shopname);
            holder.tv_id = (TextView) view.findViewById(R.id.dlg_preorder_shoplist_item_tv_id);
            holder.tv_address = (TextView) view.findViewById(R.id.dlg_preorder_shoplist_item_tv_address);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_shopname.setText(list.get(i).Sshopname);
        holder.tv_id.setText("(id:" + list.get(i).Sid + ")");
        holder.tv_address.setText(list.get(i).SshopProv + list.get(i).SshopCity + list.get(i).SshopArea + list.get(i).SshopAddress);

        return view;
    }

    class ViewHolder {
        public TextView tv_shopname;
        public TextView tv_id;
        public TextView tv_address;
    }
}

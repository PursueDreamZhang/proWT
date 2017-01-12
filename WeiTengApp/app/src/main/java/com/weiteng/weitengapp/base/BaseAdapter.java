package com.weiteng.weitengapp.base;

import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    public List<T> list;

    public BaseAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

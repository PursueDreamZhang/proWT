package com.weiteng.weitengapp.ui.view;

import com.weiteng.weitengapp.base.BaseFragment;

public abstract class Fragment extends BaseFragment {
    private String title;
    private int iconId;

    protected abstract void requestData();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return iconId;
    }

    public void setIcon(int iconId) {
        this.iconId = iconId;
    }
}
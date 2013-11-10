package com.pivotallabs;

import android.widget.BaseAdapter;

public class NotifyDataSetChangedCallbacks extends Callbacks{
    private BaseAdapter adapter;

    public NotifyDataSetChangedCallbacks(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onSuccess() {
        adapter.notifyDataSetChanged();
    }
}

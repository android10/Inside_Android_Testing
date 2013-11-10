package com.pivotallabs.tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pivotallabs.GenericAdapter;
import com.pivotallabs.R;

import java.util.List;

class RecentActivityAdapter extends GenericAdapter<RecentActivity> {

    private LayoutInflater layoutInflater;

    public RecentActivityAdapter(List<RecentActivity> recentActivities, LayoutInflater layoutInflater) {
        super(recentActivities);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(RecentActivity item, View recycleView, ViewGroup parent) {
        TextView view = (TextView) (recycleView == null ? layoutInflater.inflate(R.layout.activity_summary, parent, false) : recycleView);
        view.setText(item.getDescription());
        return view;
    }
}

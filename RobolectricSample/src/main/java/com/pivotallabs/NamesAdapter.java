package com.pivotallabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class NamesAdapter extends BaseAdapter {
    private List<String> names;

    public NamesAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = convertView == null ? createView(parent) : (TextView) convertView;
        textView.setText(names.get(position));
        return textView;
    }

    private TextView createView(ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (TextView) layoutInflater.inflate(R.layout.name_row, parent, false);
    }
}

package com.pivotallabs;

import android.view.View;

public class ViewVisibleWhileOutstandingCallbacks extends Callbacks{
    private View view;

    public ViewVisibleWhileOutstandingCallbacks(View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onComplete() {
        view.setVisibility(View.GONE);
    }
}

package com.pivotallabs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pivotallabs.R;

public class LoadingTextView extends RelativeLayout {
    public LoadingTextView(Context context) {
        super(context);
    }

    public LoadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void stopLoadingAndSetText(int text_res_id) {
        TextView textView = (TextView) findViewById(R.id.loading_text_text_view);
        textView.setText(text_res_id);
        textView.setVisibility(View.VISIBLE);

        findViewById(R.id.loading_text_spinner).setVisibility(View.GONE);
    }
}

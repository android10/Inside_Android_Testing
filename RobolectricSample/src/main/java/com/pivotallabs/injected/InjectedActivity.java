package com.pivotallabs.injected;

import android.os.Bundle;
import android.widget.TextView;
import com.google.inject.Inject;
import com.pivotallabs.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class InjectedActivity extends RoboActivity {

    @InjectResource(R.string.injected_activity_caption) String caption;
    @InjectView(R.id.injected_text_view) TextView injectedTextView;
    @Inject Date date;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.injected);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
        String formattedDate = dateFormat.format(date);
        injectedTextView.setText(caption + " - " + formattedDate);
    }
}

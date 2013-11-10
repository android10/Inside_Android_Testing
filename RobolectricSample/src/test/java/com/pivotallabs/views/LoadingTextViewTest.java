package com.pivotallabs.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.pivotallabs.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class LoadingTextViewTest {
    private LoadingTextView loadingTextView;
    private View loadingSpinner;
    private TextView loadingTextTextView;

    @Before
    public void setUp() throws Exception {
        loadingTextView = (LoadingTextView) LayoutInflater.from(new Activity()).inflate(R.layout.loading_text, null);
        loadingSpinner = loadingTextView.findViewById(R.id.loading_text_spinner);
        loadingTextTextView = (TextView) loadingTextView.findViewById(R.id.loading_text_text_view);
    }

    @Test
    public void testStopLoadingAndSetTextShouldHideTheSpinnerAndShowTheTextView() throws Exception {
        assertThat(loadingSpinner.getVisibility(), equalTo(View.VISIBLE));
        assertThat(loadingTextTextView.getVisibility(), equalTo(View.INVISIBLE));

        loadingTextView.stopLoadingAndSetText(R.string.unit_tests_ftw);

        assertThat(loadingSpinner.getVisibility(), equalTo(View.GONE));
        assertThat(loadingTextTextView.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testStopLoadingAndSEtTextShouldSetTheTextOnTheTextView() {
        loadingTextView.stopLoadingAndSetText(R.string.unit_tests_ftw);

        assertThat((String) loadingTextTextView.getText(), equalTo("Unit Tests FTW!!!"));
    }
}

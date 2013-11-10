package com.pivotallabs;

import android.view.View;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ViewVisibleWhileOutstandingCallbacksTest {

    private ViewVisibleWhileOutstandingCallbacks callbacks;
    private View view;

    @Before
    public void setUp() throws Exception {
        view = new View(null);
        view.setVisibility(View.GONE);
        callbacks = new ViewVisibleWhileOutstandingCallbacks(view);
    }

    @Test
    public void shouldSetProgressBarVisibility() throws Exception {
        callbacks.onStart();
        assertThat(view.getVisibility(), equalTo(View.VISIBLE));

        callbacks.onComplete();
        assertThat(view.getVisibility(), equalTo(View.GONE));
    }
}

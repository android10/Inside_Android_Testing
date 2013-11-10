package com.pivotallabs.tracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pivotallabs.R;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class RecentActivityAdapterTest {
    private RecentActivityAdapter adapter;
    private LinearLayout parent;
    private TextView recycleView;

    @Before
    public void setUp() throws Exception {
        RecentActivity recentActivity = new RecentActivity().setDescription("foo");
        ArrayList<RecentActivity> recentActivities = new ArrayList<RecentActivity>();
        recentActivities.add(new RecentActivity().setDescription("bar"));
        recentActivities.add(recentActivity);

        LayoutInflater layoutInflater = new Activity().getLayoutInflater();
        parent = new LinearLayout(null);
        recycleView = (TextView) layoutInflater.inflate(R.layout.activity_summary, parent);
        adapter = new RecentActivityAdapter(recentActivities, layoutInflater);
    }

    @Test
    public void shouldRecycleViews() throws Exception {
        TextView textView = (TextView) adapter.getView(1, recycleView, parent);
        assertThat(textView, sameInstance(recycleView));
        assertThat(textView.getText().toString(), equalTo("foo"));
    }

    @Test
    public void shouldCreateViews() throws Exception {
        assertThat(((TextView) adapter.getView(1, null, parent)).getText().toString(), equalTo("foo"));
    }
}

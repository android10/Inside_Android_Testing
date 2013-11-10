package com.pivotallabs;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class NamesAdapterTest {

    private ArrayList<String> names;
    private NamesAdapter namesAdapter;

    @Before
    public void setUp() throws Exception {
        names = new ArrayList<String>();
        names.add("Lauren Conrad");
        names.add("Heidi Montag");
        names.add("Kim Kardashian");
        namesAdapter = new NamesAdapter(names);
    }

    @Test
    public void testGetCount() throws Exception {
        assertThat(namesAdapter.getCount(), equalTo(names.size()));
    }

    @Test
    public void testGetItem() throws Exception {
        assertThat((String) namesAdapter.getItem(0), equalTo("Lauren Conrad"));
        assertThat((String) namesAdapter.getItem(1), equalTo("Heidi Montag"));
        assertThat((String) namesAdapter.getItem(2), equalTo("Kim Kardashian"));
    }

    @Test
    public void testGetItemId() throws Exception {
        assertThat(namesAdapter.getItemId(0), equalTo(0L));
        assertThat(namesAdapter.getItemId(1), equalTo(1L));
        assertThat(namesAdapter.getItemId(2), equalTo(2L));
    }

    @Test
    public void testGetView() throws Exception {
        TextView nameView = (TextView) namesAdapter.getView(1, null, new LinearLayout(new Activity()));
        assertThat(nameView.getText().toString(), equalTo("Heidi Montag"));
    }

    @Test
    public void shouldRecycleProvidedViews() throws Exception {
        TextView existingView = new TextView(null);
        TextView nameView = (TextView) namesAdapter.getView(2, existingView, new LinearLayout(new Activity()));
        assertThat(nameView, sameInstance(existingView));
    }
}

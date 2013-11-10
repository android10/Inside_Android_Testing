package com.pivotallabs;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class GenericAdapterTest {

    private ArrayList<String> strings;
    private TestAdapter adapter;

    @Before
    public void setUp() throws Exception {
        strings = new ArrayList<String>();
        strings.add("one");
        strings.add("two");
        strings.add("three");
        adapter = new TestAdapter(strings);
    }

    @Test
    public void getCount_shouldReturnListSize() throws Exception {
        assertThat(adapter.getCount(), equalTo(3));
        strings.add("four");
        assertThat(adapter.getCount(), equalTo(4));
    }

    @Test
    public void getItem_shouldReturnObjectAtIndex() throws Exception {
        assertThat(adapter.getItem(0), equalTo("one"));
        assertThat(adapter.getItem(1), equalTo("two"));
        assertThat(adapter.getItem(2), equalTo("three"));
    }

    @Test
    public void getItemId_shouldReturnIndex() throws Exception {
        assertThat(adapter.getItemId(666), equalTo(666L));
    }

    @Test
    public void getView_shouldCallThroughToGetView() throws Exception {
        View convertView = new View(null);
        ViewGroup parent = new LinearLayout(null);

        adapter.getView(1, convertView, parent);
        
        assertThat(adapter.item, equalTo("two"));
        assertThat(adapter.recycleView, sameInstance(convertView));
        assertThat(adapter.parent, sameInstance(parent));
    }

    static class TestAdapter extends GenericAdapter<String> {

        private String item;
        private View recycleView;
        private ViewGroup parent;

        public TestAdapter(ArrayList<String> strings) {
            super(strings);
        }
        @Override
        public View getView(String item, View recycleView, ViewGroup parent) {
            this.item = item;
            this.recycleView = recycleView;
            this.parent = parent;
            return null;
        }

    }
}

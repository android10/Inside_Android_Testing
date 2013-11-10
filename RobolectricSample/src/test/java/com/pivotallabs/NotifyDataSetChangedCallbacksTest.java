package com.pivotallabs;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class NotifyDataSetChangedCallbacksTest {
    private TestListAdapter testListAdapter;
    private NotifyDataSetChangedCallbacks notifyDataSetChangedCallbacks;

    @Before
    public void setUp() throws Exception {
        testListAdapter = new TestListAdapter();
        notifyDataSetChangedCallbacks = new NotifyDataSetChangedCallbacks(testListAdapter);
    }

    @Test
    public void onSuccessShouldNotifyDataSetChanged() throws Exception {
        notifyDataSetChangedCallbacks.onSuccess();
        assertThat(testListAdapter.notifyDataSetChangedWasCalled, equalTo(true));
    }

    private class TestListAdapter extends BaseAdapter {
        public boolean notifyDataSetChangedWasCalled;

        @Override
        public void notifyDataSetChanged() {
            notifyDataSetChangedWasCalled = true;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}

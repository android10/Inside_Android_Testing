package com.pivotallabs;

import android.widget.ListView;
import android.widget.TextView;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class NamesActivityTest {

    private NamesActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = new NamesActivity();
    }

    @Test
    public void shouldShowAListOfNames() throws Exception {
        activity.onCreate(null);
                        
        ListView namesListView = (ListView) activity.findViewById(R.id.names_list);

        TextView nameRow = (TextView) namesListView.getChildAt(1);

        assertThat(nameRow.getText().toString(), equalTo("Donald Rumsfeld"));
    }
}

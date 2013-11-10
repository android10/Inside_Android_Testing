package com.pivotallabs;

import android.view.View;
import android.widget.EditText;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ViewEnablingTextWatcherTest {
    private View toEnable;
    private EditText editText1;
    private EditText editText2;

    @Before
    public void setUp() throws Exception {
        toEnable = new View(null);
        editText1 = new EditText(null);
        editText2 = new EditText(null);
        toEnable.setEnabled(false);
        new ViewEnablingTextWatcher(toEnable, editText1, editText2);
    }

    @Test
    public void shouldEnableTheViewWhenAllEditTextsHaveText() throws Exception {
        assertThat(toEnable.isEnabled(), equalTo(false));
        editText1.setText("foo");
        assertThat(toEnable.isEnabled(), equalTo(false));
        editText2.setText("bar");
        assertThat(toEnable.isEnabled(), equalTo(true));
    }

    @Test
    public void shouldNotEnableTheViewIfTheAnyOfTheEditTextsContainWhitespace() throws Exception {
        assertThat(toEnable.isEnabled(), equalTo(false));
        editText1.setText("foo");
        assertThat(toEnable.isEnabled(), equalTo(false));
        editText2.setText(" \t \n ");
        assertThat(toEnable.isEnabled(), equalTo(false));
    }

    @Test
    public void shouldDisableTheViewWhenOneOfTheEditTextsHaveEmptyText() throws Exception {
        editText1.setText("foo");
        editText2.setText("bar");
        assertThat(toEnable.isEnabled(), equalTo(true));
        editText2.setText("");
        assertThat(toEnable.isEnabled(), equalTo(false));
    }
}

package com.pivotallabs.util;


import org.junit.Test;

import static com.pivotallabs.util.Strings.isEmptyOrWhitespace;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class StringsTest {

    @Test
    public void testIsEmptyOrWhitespace() {
        assertThat(isEmptyOrWhitespace(""), equalTo(true));
        assertThat(isEmptyOrWhitespace(" "), equalTo(true));
        assertThat(isEmptyOrWhitespace(" \n \t "), equalTo(true));
        assertThat(isEmptyOrWhitespace("foo"), equalTo(false));
        assertThat(isEmptyOrWhitespace(" bar "), equalTo(false));
        assertThat(isEmptyOrWhitespace(" \tbar \n"), equalTo(false));
    }
}

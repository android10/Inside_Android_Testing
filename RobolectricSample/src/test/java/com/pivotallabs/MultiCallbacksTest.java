package com.pivotallabs;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MultiCallbacksTest {
    private TestCallbacks testCallbacks0;
    private TestCallbacks testCallbacks1;
    private MultiCallbacks multiCallbacks;

    @Before
    public void setUp() throws Exception {
        testCallbacks0 = new TestCallbacks();
        testCallbacks1 = new TestCallbacks();
        multiCallbacks = new MultiCallbacks(testCallbacks1, testCallbacks0);
    }

    @Test
    public void onStart_shouldCallThroughToAllProvidedCallbacks() throws Exception {
        multiCallbacks.onStart();
        assertThat(testCallbacks0.startWasCalled, equalTo(true));
        assertThat(testCallbacks1.startWasCalled, equalTo(true));
    }

    @Test
    public void onSuccess_shouldCallThroughToAllProvidedCallbacks() throws Exception {
        multiCallbacks.onSuccess();
        assertThat(testCallbacks0.successWasCalled, equalTo(true));
        assertThat(testCallbacks1.successWasCalled, equalTo(true));
    }

    @Test
    public void onFailure_shouldCallThroughToAllProvidedCallbacks() throws Exception {
        multiCallbacks.onFailure();
        assertThat(testCallbacks0.failureWasCalled, equalTo(true));
        assertThat(testCallbacks1.failureWasCalled, equalTo(true));
    }

    @Test
    public void onComplete_shouldCallThroughToAllProvidedCallbacks() throws Exception {
        multiCallbacks.onComplete();
        assertThat(testCallbacks0.completeWasCalled, equalTo(true));
        assertThat(testCallbacks1.completeWasCalled, equalTo(true));
    }
}

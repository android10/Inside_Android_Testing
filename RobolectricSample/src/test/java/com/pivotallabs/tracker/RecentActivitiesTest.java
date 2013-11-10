package com.pivotallabs.tracker;

import android.app.Activity;
import com.pivotallabs.Callbacks;
import com.pivotallabs.TestCallbacks;
import com.pivotallabs.TestResponses;
import com.pivotallabs.api.ApiRequest;
import com.pivotallabs.api.TestApiGateway;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class RecentActivitiesTest {
    private TestApiGateway apiGateway;
    private RecentActivities recentActivities;

    @Before
    public void setUp() throws Exception {
        apiGateway = new TestApiGateway();

        AuthenticationGateway authenticationGateway = new AuthenticationGateway(apiGateway, new Activity());
        authenticationGateway.signIn("user", "pass", new Callbacks());
        apiGateway.simulateResponse(200, TestResponses.AUTH_SUCCESS);

        recentActivities = new RecentActivities(apiGateway, authenticationGateway);
    }

    @Test
    public void update_shouldMakeRequest() throws Exception {
        recentActivities.update(new TestCallbacks());
        ApiRequest expectedRequest = new RecentActivityRequest("c93f12c");
        assertThat(apiGateway.getLatestRequest(), equalTo(expectedRequest));
    }

    @Test
    public void update_shouldCreateRecentActivityObjectsFromResponse() throws Exception {
        recentActivities.update(new TestCallbacks());
        apiGateway.simulateResponse(200, TestResponses.RECENT_ACTIVITY);

        assertThat(recentActivities.size(), equalTo(2));

        RecentActivity recentActivity0 = recentActivities.get(0);
        assertThat(recentActivity0.getDescription(),
                equalTo("I changed the 'request' for squidward. \"Add 'Buyout'\""));

        RecentActivity recentActivity1 = recentActivities.get(1);
        assertThat(recentActivity1.getDescription(),
                equalTo("Spongebob Square edited \"Application tracks listing clicks\""));
    }

    @Test
    public void update_shouldFireCallbacks_success() throws Exception {
        TestCallbacks callbacks = new TestCallbacks();
        recentActivities.update(callbacks);

        assertThat(callbacks.startWasCalled, equalTo(true));

        apiGateway.simulateResponse(200, TestResponses.RECENT_ACTIVITY);
        assertThat(callbacks.successWasCalled, equalTo(true));
        assertThat(callbacks.failureWasCalled, equalTo(false));
        assertThat(callbacks.completeWasCalled, equalTo(true));
    }

    @Test
    public void update_shouldFireCallbacks_failure() throws Exception {
        TestCallbacks callbacks = new TestCallbacks();
        recentActivities.update(callbacks);

        assertThat(callbacks.startWasCalled, equalTo(true));

        apiGateway.simulateResponse(500, "ERROR");
        assertThat(callbacks.successWasCalled, equalTo(false));
        assertThat(callbacks.failureWasCalled, equalTo(true));
        assertThat(callbacks.completeWasCalled, equalTo(true));
    }

    @Test
    public void update_shouldClearExitingItems() throws Exception {
        recentActivities.update(new TestCallbacks());
        apiGateway.simulateResponse(200, TestResponses.RECENT_ACTIVITY);
        assertThat(recentActivities.size(), equalTo(2));

        recentActivities.update(new TestCallbacks());
        apiGateway.simulateResponse(200, TestResponses.RECENT_ACTIVITY);
        assertThat(recentActivities.size(), equalTo(2));
    }
}

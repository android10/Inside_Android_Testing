package com.pivotallabs.tracker;

import android.app.Activity;
import com.pivotallabs.TestCallbacks;
import com.pivotallabs.api.TestApiGateway;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.MODE_PRIVATE;
import static com.pivotallabs.TestResponses.AUTH_SUCCESS;
import static com.pivotallabs.tracker.AuthenticationGateway.TRACKER_AUTH_PREF_KEY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AuthenticationGatewayTest {

    private TestApiGateway apiGateway;
    private TestCallbacks callbacks;
    private Activity context;
    private AuthenticationGateway authenticationGateway;

    @Before
    public void setUp() throws Exception {
        apiGateway = new TestApiGateway();
        context = new Activity();
        callbacks = new TestCallbacks();
        authenticationGateway = new AuthenticationGateway(apiGateway, context);
    }

    @Test
    public void shouldMakeARemoteCallWhenSigningIn() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        String urlString = apiGateway.getLatestRequest().getUrlString();
        assertThat(urlString, equalTo("https://www.pivotaltracker.com/services/v3/tokens/active"));
    }

    @Test
    public void shouldSendUsernameAndPassword() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        TrackerAuthenticationRequest request = (TrackerAuthenticationRequest) apiGateway.getLatestRequest();
        assertThat(request, equalTo(new TrackerAuthenticationRequest("spongebob", "squidward")));
    }

    @Test
    public void authenticated_shouldReturnTrueWhenSignedIn() throws Exception {
        assertThat(authenticationGateway.isAuthenticated(), equalTo(false));
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(200, AUTH_SUCCESS);
        assertThat(authenticationGateway.isAuthenticated(), equalTo(true));
    }

    @Test
    public void signOutShouldRemoveTheSharedPreferences() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(200, AUTH_SUCCESS);
        authenticationGateway.signOut();
        assertThat(getStoredGuid(), equalTo(""));
    }

    @Test
    public void shouldCallSuccessWhenAuthenticationSucceeds() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(200, AUTH_SUCCESS);
        assertThat(callbacks.successWasCalled, equalTo(true));
        assertThat(callbacks.failureWasCalled, equalTo(false));
        assertThat(callbacks.completeWasCalled, equalTo(true));
    }

    @Test
    public void shouldCallFailureWhenAuthenticationFails() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(401, "Access Denied");
        assertThat(callbacks.failureWasCalled, equalTo(true));
        assertThat(callbacks.successWasCalled, equalTo(false));
        assertThat(callbacks.completeWasCalled, equalTo(true));
    }

    @Test
    public void shouldCallFailureWhenServerReturnsError() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(500, "ERROR");
        assertThat(callbacks.failureWasCalled, equalTo(true));
        assertThat(callbacks.successWasCalled, equalTo(false));
        assertThat(callbacks.completeWasCalled, equalTo(true));
    }

    @Test
    public void shouldStoreApiTokenInPrefs() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(200, AUTH_SUCCESS);
        assertThat(getStoredGuid(), equalTo("c93f12c"));
    }

    @Test
    public void getGuid_shouldReturnGuidFromResponse() throws Exception {
        authenticationGateway.signIn("spongebob", "squidward", callbacks);
        apiGateway.simulateResponse(200, AUTH_SUCCESS);
        assertThat(authenticationGateway.getToken(), equalTo("c93f12c"));
    }

    private String getStoredGuid() {
        return context.getSharedPreferences(TRACKER_AUTH_PREF_KEY, MODE_PRIVATE).getString("guid", "");
    }
}

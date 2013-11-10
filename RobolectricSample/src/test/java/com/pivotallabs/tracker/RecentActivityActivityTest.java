package com.pivotallabs.tracker;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.pivotallabs.R;
import com.pivotallabs.TestResponses;
import com.pivotallabs.api.ApiRequest;
import com.pivotallabs.api.TestApiGateway;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.TestHelper.signIn;
import static org.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class RecentActivityActivityTest {

    private RecentActivityActivity activity;
    private AuthenticationGateway authenticationGateway;
    private TestApiGateway apiGateway;
    private ListView activityListView;

    @Before
    public void setUp() throws Exception {
        signIn();
        createActivity();

        authenticationGateway = new AuthenticationGateway(apiGateway, activity);
        activityListView = (ListView) activity.findViewById(R.id.recent_activity_list);
    }

    @Test
    public void shouldShowTheSignInDialogIfNotCurrentlySignedIn() throws Exception {
        signOutAndReCreateActivity();

        assertThat(authenticationGateway.isAuthenticated(), equalTo(false));
        assertThat(activity.signInDialog.isShowing(), equalTo(true));
    }

    @Test
    public void shouldNotShowTheSignInDialogIfSignedIn() {
        assertThat(activity.signInDialog, nullValue());
    }

    @Test
    public void shouldRetrieveRecentActivityUponSuccessfulSignIn() throws Exception {
        signOutAndReCreateActivity();
        signInThroughDialog();

        ApiRequest expectedRequest = new RecentActivityRequest("c93f12c");
        assertThat(apiGateway.getLatestRequest(), equalTo(expectedRequest));
    }

    @Test
    public void onCreate_shouldRetrieveRecentActivityWhenSignedIn() {
        assertThat(apiGateway.getLatestRequest(),
                equalTo((ApiRequest) new RecentActivityRequest("c93f12c")));
    }

    @Test
    public void shouldPopulateViewWithRetrievedRecentActivity() throws Exception {
        apiGateway.simulateResponse(200, TestResponses.RECENT_ACTIVITY);
        String firstRowText = shadowOf((TextView) activityListView.getChildAt(0)).innerText();
        assertThat(firstRowText, equalTo("I changed the 'request' for squidward. \"Add 'Buyout'\""));
    }

    @Test
    public void shouldShowProgressBarWhileRequestIsOutstanding() throws Exception {
        View footerView = shadowOf(activityListView).getFooterViews().get(0);

        assertThat(footerView.getVisibility(), equalTo(View.VISIBLE));

        apiGateway.simulateResponse(200, TestResponses.RECENT_ACTIVITY);

        assertThat(footerView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void shouldFinishWhenSignInDialogIsDismissedWithoutSuccessfulSignIn() {
        signOutAndReCreateActivity();

        activity.signInDialog.cancel();

        assertThat(shadowOf(activity).isFinishing(), equalTo(true));
    }

    @Test
    public void shouldSignOutWhenTheSignOutButtonIsClicked() throws Exception {
        TestMenu menu = new TestMenu();
        menu.add("garbage that should be cleared upon onPrepareOptionsMenu");

        activity.onPrepareOptionsMenu(menu);

        TestMenuItem signOutMenuItem = (TestMenuItem) menu.getItem(0);
        assertThat(signOutMenuItem.isEnabled(), equalTo(true));
        assertThat(signOutMenuItem.getTitle().toString(), equalTo("Sign Out"));

        signOutMenuItem.click();
        assertThat(authenticationGateway.isAuthenticated(), equalTo(false));
        assertThat(shadowOf(activity).isFinishing(), equalTo(true));
    }

    @Test
    public void signOutButtonShouldBeDisabledWhenNotSignedIn() throws Exception {
        authenticationGateway.signOut();
        TestMenu menu = new TestMenu();

        activity.onPrepareOptionsMenu(menu);

        TestMenuItem signOutMenuItem = (TestMenuItem) menu.getItem(0);
        assertThat(signOutMenuItem.isEnabled(), equalTo(false));
        assertThat(signOutMenuItem.getTitle().toString(), equalTo("Sign Out"));
    }

    private void signOutAndReCreateActivity() {
        authenticationGateway.signOut();
        createActivity();
    }

    private void signInThroughDialog() throws Exception {
        assertThat(activity.signInDialog.isShowing(), equalTo(true));
        ((EditText) activity.signInDialog.findViewById(R.id.username)).setText("user");
        ((EditText) activity.signInDialog.findViewById(R.id.password)).setText("pass");
        activity.signInDialog.findViewById(R.id.sign_in_button).performClick();

        apiGateway.simulateResponse(200, TestResponses.AUTH_SUCCESS);
        assertThat(activity.signInDialog.isShowing(), equalTo(false));
    }

    private void createActivity() {
        apiGateway = new TestApiGateway();
        activity = new RecentActivityActivity();
        activity.apiGateway = apiGateway;
        activity.onCreate(null);
    }
}

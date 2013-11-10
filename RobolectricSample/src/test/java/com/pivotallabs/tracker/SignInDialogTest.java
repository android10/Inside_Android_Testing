package com.pivotallabs.tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import com.pivotallabs.R;
import com.pivotallabs.TestResponses;
import com.pivotallabs.api.ApiRequest;
import com.pivotallabs.api.TestApiGateway;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.robolectric.Robolectric.clickOn;
import static org.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricTestRunner.class)
public class SignInDialogTest {
    private View signInButton;
    private TextView usernameEditText;
    private TextView passwordEditText;
    private TestApiGateway apiGateway;
    private SignInDialog signInDialog;

    @Before
    public void setUp() throws Exception {
        apiGateway = new TestApiGateway();
        signInDialog = new SignInDialog(new Activity(), new AuthenticationGateway(apiGateway, new Activity()));

        signInDialog.show();

        usernameEditText = (TextView) signInDialog.findViewById(R.id.username);
        passwordEditText = (TextView) signInDialog.findViewById(R.id.password);
        signInButton = signInDialog.findViewById(R.id.sign_in_button);
    }

    @Test
    public void shouldNotEnableTheSignInButtonUntilUsernameAndPasswordFieldsHaveText() throws Exception {
        assertThat(signInButton.isEnabled(), equalTo(false));
        usernameEditText.setText("Sponge Bob");
        assertThat(signInButton.isEnabled(), equalTo(false));
        passwordEditText.setText("squidward");
        assertThat(signInButton.isEnabled(), equalTo(true));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotBeAbleToSignInIfUserNameHasNotBeenSet() throws Exception {
        passwordEditText.setText("squidward");
        clickOn(signInButton);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotBeAbleToSignInIfPasswordHasNotBeenSet() throws Exception {
        usernameEditText.setText("Sponge Bob");
        clickOn(signInButton);
    }

    @Test
    public void shouldCallTheTrackerApiUponClickingSignIn() throws Exception {
        usernameEditText.setText("Sponge Bob");
        passwordEditText.setText("squidward");
        clickOn(signInButton);
        assertRequestWasMade(apiGateway, new TrackerAuthenticationRequest("Sponge Bob", "squidward"));
    }

    @Test
    public void shouldDisableTheSignInButtonWhileRequestIsOutstanding() {
        usernameEditText.setText("Sponge Bob");
        passwordEditText.setText("squidward");
        clickOn(signInButton);
        assertThat(signInButton.isEnabled(), equalTo(false));
    }

    @Test
    public void shouldReEnableTheSignInButtonIfSignInFails() throws Exception {
        usernameEditText.setText("Sponge Bob");
        passwordEditText.setText("squidward");
        clickOn(signInButton);
        apiGateway.simulateResponse(401, "Access Denied");
        assertThat(signInButton.isEnabled(), equalTo(true));
    }

    @Test
    public void shouldDismissWhenSuccessfullySignedIn() throws Exception {
        usernameEditText.setText("Spongebob");
        passwordEditText.setText("squidward");
        clickOn(signInButton);

        assertThat(signInDialog.isShowing(), equalTo(true));

        apiGateway.simulateResponse(200, TestResponses.AUTH_SUCCESS);

        assertThat(signInDialog.isShowing(), equalTo(false));
    }

    @Test
    public void shouldNotDismissWhenUnSuccessfullySignedIn() throws Exception {
        usernameEditText.setText("Spongebob");
        passwordEditText.setText("squidward");
        clickOn(signInButton);

        assertThat(signInDialog.isShowing(), equalTo(true));

        apiGateway.simulateResponse(401, "Access Denied");

        assertThat(signInDialog.isShowing(), equalTo(true));
    }

    @Test
    public void shouldShowAlertDialogWhenUnSuccessfullySignedIn() throws Exception {
        usernameEditText.setText("Spongebob");
        passwordEditText.setText("squidward");
        clickOn(signInButton);

        apiGateway.simulateResponse(401, "Access Denied");

        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(alertDialog.isShowing(), equalTo(true));
        assertThat(((String) shadowOf(alertDialog).getTitle()), equalTo("Error"));
        assertThat(((String) shadowOf(alertDialog).getMessage()), equalTo("Username/Password combination is not recognized."));
        assertNotNull(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE));
    }

    private void assertRequestWasMade(TestApiGateway apiGateway, ApiRequest request) {
        assertThat(apiGateway.getLatestRequest(), equalTo(request));
    }
}

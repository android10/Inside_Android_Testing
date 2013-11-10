package com.pivotallabs.tracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.pivotallabs.Callbacks;
import com.pivotallabs.EmptyOnClickListener;
import com.pivotallabs.R;
import com.pivotallabs.ViewEnablingTextWatcher;

public class SignInDialog extends Dialog {

    AuthenticationGateway authenticationGateway;
    private View signInButton;

    public SignInDialog(Context context, AuthenticationGateway authenticationGateway) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        this.authenticationGateway = authenticationGateway;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_dialog);

        final EditText usernameEditText = (EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);

        signInButton = findViewById(R.id.sign_in_button);

        new ViewEnablingTextWatcher(signInButton, usernameEditText, passwordEditText);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton.setEnabled(false);
                authenticationGateway.signIn(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        new SignInCallbacks());
            }
        });
    }

    private class SignInCallbacks extends Callbacks{
        @Override
        public void onSuccess() {
            dismiss();
        }

        @Override
        public void onFailure() {
            Resources resources = getContext().getResources();
            new AlertDialog.Builder(getContext())
                    .setTitle(resources.getString(R.string.error))
                    .setMessage(resources.getString(R.string.unknown_user_pass))
                    .setPositiveButton(resources.getString(R.string.ok), new EmptyOnClickListener())
                    .show();
        }

        @Override
        public void onComplete() {
            signInButton.setEnabled(true);
        }
    }
}

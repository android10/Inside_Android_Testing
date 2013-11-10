package com.pivotallabs.tracker;

import android.content.Context;
import android.content.SharedPreferences;
import com.pivotallabs.Callbacks;
import com.pivotallabs.api.*;
import com.pivotallabs.util.Strings;

import java.io.IOException;

public class AuthenticationGateway {
    static final String TRACKER_AUTH_PREF_KEY = "tracker-auth";
    private static final String GUID_KEY = "guid";
    public ApiGateway apiGateway;
    private SharedPreferences sharedPreferences;

    public AuthenticationGateway(ApiGateway apiGateway, Context context) {
        this.apiGateway = apiGateway;
        sharedPreferences = context.getSharedPreferences(TRACKER_AUTH_PREF_KEY, Context.MODE_PRIVATE);
    }

    public void signIn(String username, String password, Callbacks responseCallbacks) {
        apiGateway.makeRequest(
                new TrackerAuthenticationRequest(username, password),
                new AuthenticationApiResponseCallbacks(responseCallbacks, sharedPreferences));
    }

    public boolean isAuthenticated() {
        return !Strings.isEmptyOrWhitespace(getToken());
    }

    public void signOut() {
        sharedPreferences.edit().clear().commit();
    }

    public String getToken() {
        return sharedPreferences.getString(GUID_KEY, "");
    }

    private static class AuthenticationApiResponseCallbacks implements ApiResponseCallbacks<XmlApiResponse> {
        private Callbacks callbacks;
        private SharedPreferences sharedPreferences;

        public AuthenticationApiResponseCallbacks(Callbacks callbacks, SharedPreferences sharedPreferences) {
            this.callbacks = callbacks;
            this.sharedPreferences = sharedPreferences;
        }

        @Override
        public void onSuccess(XmlApiResponse response) throws IOException {
            String guid = Xmls.getTextContentOfChild(response.getResponseDocument(), "guid");
            sharedPreferences.edit().putString(GUID_KEY, guid).commit();
            callbacks.onSuccess();
        }

        @Override
        public void onFailure(ApiResponse response) {
            callbacks.onFailure();
        }

        @Override
        public void onComplete() {
            callbacks.onComplete();
        }
    }
}

package com.pivotallabs;

public interface AuthenticationCallbacks {
    public void onSuccess(String apiToken);
    public void onFailure();
}

package com.pivotallabs.api;

import java.io.IOException;

public interface ApiResponseCallbacks<T extends ApiResponse> {
    public void onSuccess(T response) throws IOException;

    public void onFailure(ApiResponse response);

    public void onComplete();
}

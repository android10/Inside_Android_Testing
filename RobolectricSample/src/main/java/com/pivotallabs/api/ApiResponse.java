package com.pivotallabs.api;

import java.io.InputStream;

public abstract class ApiResponse {
    protected int httpResponseCode;

    public ApiResponse(int httpCode) {
        this.httpResponseCode = httpCode;
    }

    abstract void consumeResponse(InputStream responseBody) throws Exception;

    public int getResponseCode() {
        return httpResponseCode;
    }

    public boolean isSuccess() {
        return httpResponseCode >= 200 && httpResponseCode < 300;
    }

    public boolean isUnauthorized() {
        return httpResponseCode == 401;
    }
}
